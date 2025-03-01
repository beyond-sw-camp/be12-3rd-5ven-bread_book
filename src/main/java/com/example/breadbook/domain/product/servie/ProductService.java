package com.example.breadbook.domain.product.servie;

import com.example.breadbook.domain.book.BookRepository;
import com.example.breadbook.domain.book.model.Book;
import com.example.breadbook.domain.category.CategoryRepository;
import com.example.breadbook.domain.category.model.Category;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.model.ProductDto;
import com.example.breadbook.domain.product.repository.ProductImageRepository;
import com.example.breadbook.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    private final ImageService imageService;
    private final ProductImageService productImageService;
    private final LocalImageService localImageService;


    //, Book book, Category category
    public ProductDto.ProductResponse registerProduct(ProductDto.ProductRegister dto, Member member, MultipartFile[] imgFiles) {
        Book book = bookRepository.findById(dto.getBookIdx()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 책"));
        Category category = categoryRepository.findById(dto.getCategoryIdx()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리"));
        // Optional 로 해줘도 될까?

        Product product = productRepository.save(dto.toEntity(member, book, category));

        List<String> uploadFilePaths = localImageService.upload(imgFiles);


        productImageService.createProductImage(uploadFilePaths, product);

        ProductDto.ProductResponse response =  ProductDto.ProductResponse.of(product);
        response.setProductImageList(uploadFilePaths);
        return response;

    }
}
