package com.example.breadbook.domain.product.service;

import com.example.breadbook.domain.book.BookRepository;
import com.example.breadbook.domain.book.model.Book;
import com.example.breadbook.domain.member.service.MemberService;
import com.example.breadbook.domain.product.model.ProductImage;
import com.example.breadbook.domain.product.repository.CategoryRepository;
import com.example.breadbook.domain.product.model.Category;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.model.ProductDto;
import com.example.breadbook.domain.product.repository.ProductImageRepository;
import com.example.breadbook.domain.product.repository.ProductRepository;
import com.example.breadbook.domain.wish.WishRepository;
import com.example.breadbook.domain.wish.model.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final WishRepository wishRepository;

    private final ProductImageService productImageService;
    private final LocalImageService localImageService;
    private final MemberService memberService;


    @Transactional
    public ProductDto.ProductResponse registerProduct(ProductDto.RegisterRequest dto, Member member, MultipartFile[] imgFiles) {
        Book book = bookRepository.findById(dto.getBookIdx()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 책"));
        Category category = categoryRepository.findByName(dto.getCategoryName()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리"));

        Product product = productRepository.save(dto.toEntity(member, book, category));

        List<String> uploadFilePaths = localImageService.upload(imgFiles);
        List<ProductImage> productImageList = productImageService.createProductImage(uploadFilePaths, product);


        ProductDto.ProductResponse response =  ProductDto.ProductResponse.of(product, uploadFilePaths);
        response.setProductImageList(uploadFilePaths);
        return response;
    }

    @Transactional
    public ProductDto.ProductResponse updateProduct(Long productIdx, Member currentUser, ProductDto.RegisterRequest dto) throws Exception {
        if (!productRepository.existsByIdx(productIdx)) {
            throw new Exception("존재하지 않는 상품입니다.");
        } else if (!currentUser.getIdx().equals(productRepository.findByIdx(productIdx).getMember().getIdx())) {
            throw new Exception("수정 권한이 없는 사용자입니다.");
        } else {
            Product updated = productRepository.findByIdx(productIdx);
            updated.setBook(bookRepository.findById(dto.getBookIdx()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 책")));
            updated.setCategory(categoryRepository.findByName(dto.getCategoryName()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리")));
            updated.setPrice(dto.getPrice());
            updated.setBookCondition(dto.getBookCondition());
            updated.setTradeMethod(dto.getTradeMethod());
            updated.setTradeLocation(dto.getTradeLocation());
            updated.setDescription(dto.getDescription());
//            List<ProductImage> productImageList = productImageRepository.findByProductIdx(productIdx);
//            List<String> imgUrlList = new ArrayList<>();
//            for (ProductImage productImage : productImageList) {
//                imgUrlList.add(productImage.getProductImgUrl());
//            }
//            Product product = dto.toEntity(currentUser, book, category);
////            product.setIdx(productIdx);
//            productRepository.save(product);
//            ProductDto.ProductResponse response = new ProductDto.ProductResponse();
//            response = ProductDto.ProductResponse.of(product, imgUrlList);
            return ProductDto.ProductResponse.of(updated);
        }
    }

    @Transactional
    public ProductDto.ProductResponse getProductItem(Long productIdx) {
        Product product = productRepository.findByIdx(productIdx);
        List<ProductImage> productImageList = productImageRepository.findByProductIdx(productIdx);
        List<String> imgUrlList = new ArrayList<>();
        for (ProductImage productImage : productImageList) {
            imgUrlList.add(productImage.getProductImgUrl());
        }
        ProductDto.ProductResponse response = ProductDto.ProductResponse.of(product, imgUrlList);
        return response;
    }

    @Transactional
    public Page<ProductDto.ListResponse> getProductList(Member currentUser, Pageable pageable) {
        // 현재 로그인한 사용자의 위시리스트 조회
        List<Wish> wishList = wishRepository.findAllByMemberAndCanceledFalse(currentUser);
        // 로그인하지 않은 상태라면? => 어떻게 할 지 생각해보기.. ㅠㅠ

        // wishList를 productIdx 기준으로 Map 변환 (N+1) 방지
        Map<Long, Boolean> wishCanceledMap = wishList.stream().collect(Collectors.toMap(wish -> wish.getProduct().getIdx(), Wish::isCanceled));
        /* 아래와 동일한 코드..
        Map<Long, Boolean> wishCanceledMap = new HashMap<>();

        for (Wish wish : wishList) {
            Long productId = wish.getProduct().getIdx(); // 키: Product의 Idx
            Boolean isCanceled = wish.isCanceled();     // 값: 등록된 Wish의 isCanceled 취소 여부
            wishCanceledMap.put(productId, isCanceled);
        }
        */

        return productRepository.findAll(pageable).map(product -> new ProductDto.ListResponse(
                product.getMember().getScore(),
                product.getIdx(),
                product.getBook().getTitle(),
                product.getBook().getAuthor(),
                product.getBook().getPublisher(),
                product.getBook().getPublicationDate(),
                product.getPrice(),
                product.getBookCondition(),
                product.getProductImageList().isEmpty() ? null : product.getProductImageList().get(0).getProductImgUrl(),
                wishCanceledMap.getOrDefault(product.getIdx(), true)
        ));
    }

    @Transactional
    public ProductDto.DeleteResponse deleteProduct(Long productIdx, Member currentUser) throws Exception {
        Product product = productRepository.findByIdx(productIdx);
        if (!(product.getMember().getIdx()).equals(currentUser.getIdx())) {
            throw new Exception("상품 삭제 권한이 없는 사용자입니다.");
        }
        else {
            productRepository.deleteById(productIdx);
            ProductDto.DeleteResponse dto = new ProductDto.DeleteResponse();
            dto.setIdx(productIdx);
            return dto;
        }
    }




}
