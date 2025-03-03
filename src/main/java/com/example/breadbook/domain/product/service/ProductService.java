package com.example.breadbook.domain.product.service;

import com.example.breadbook.domain.book.BookRepository;
import com.example.breadbook.domain.book.model.Book;
import com.example.breadbook.domain.member.service.MemberService;
import com.example.breadbook.domain.product.repository.CategoryRepository;
import com.example.breadbook.domain.product.model.Category;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.model.ProductDto;
import com.example.breadbook.domain.product.repository.ProductRepository;
import com.example.breadbook.domain.wish.WishRepository;
import com.example.breadbook.domain.wish.model.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final WishRepository wishRepository;

    private final ProductImageService productImageService;
    private final LocalImageService localImageService;
    private final MemberService memberService;


    //, Book book, Category category
    public ProductDto.ProductResponse registerProduct(ProductDto.ProductRegister dto, Member member, MultipartFile[] imgFiles) {
        Book book = bookRepository.findById(dto.getBookIdx()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 책"));
        Category category = categoryRepository.findByName(dto.getCategoryName()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리"));

        Product product = productRepository.save(dto.toEntity(member, book, category));

        List<String> uploadFilePaths = localImageService.upload(imgFiles);
        productImageService.createProductImage(uploadFilePaths, product);

        ProductDto.ProductResponse response =  ProductDto.ProductResponse.of(product);
        response.setProductImageList(uploadFilePaths);
        return response;
    }

    public Page<ProductDto.ListResponse> getProductList(Member currentUser, Pageable pageable) {
        // 현재 로그인한 사용자의 위시리스트 조회
        List<Wish> wishList = wishRepository.findByMemberIdx(currentUser.getIdx());
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
                product.getBook().getTitle(),
                product.getBook().getAuthor(),
                product.getBook().getPublisher(),
                product.getBook().getPublicationDate(),
                product.getPrice(),
                product.getBookCondition(),
                product.getProductImageList().isEmpty() ? null : product.getProductImageList().get(0).getProductImgUrl(),
                wishCanceledMap.getOrDefault(product.getIdx(), false)
        ));
    }
}
