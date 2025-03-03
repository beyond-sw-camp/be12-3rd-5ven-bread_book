package com.example.breadbook.domain.product.service;

import com.example.breadbook.domain.book.BookRepository;
import com.example.breadbook.domain.book.model.Book;
import com.example.breadbook.domain.product.repository.CategoryRepository;
import com.example.breadbook.domain.product.model.Category;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.model.ProductDto;
import com.example.breadbook.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    private final ProductImageService productImageService;
    private final LocalImageService localImageService;


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

    public Page<ProductDto.ListResponse> getProductList(Pageable pageable) {
        return productRepository.findAll(pageable).map(product -> new ProductDto.ListResponse(
                product.getBook().getTitle(),
                product.getBook().getAuthor(),
                product.getBook().getPublisher(),
                product.getBook().getPublicationDate(),
                product.getPrice(),
                product.getBookCondition(),
                product.getProductImageList().isEmpty() ? null : product.getProductImageList().get(0).getProductImgUrl()
        ));
    }
}
