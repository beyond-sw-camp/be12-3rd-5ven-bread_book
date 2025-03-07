package com.example.breadbook.domain.product.service;

import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.model.ProductImage;
import com.example.breadbook.domain.product.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public List<ProductImage> createProductImage(List<String> uploadFilePaths, Product product) {
        for (String uploadFilePath : uploadFilePaths) {
            productImageRepository.save(ProductImage.builder()
                                    .productImgUrl(uploadFilePath)
                                    .product(product)
                                    .build());
        }
        List<ProductImage> productImageList = new ArrayList<>();
        for (String uploadFilePath : uploadFilePaths) {
            ProductImage productImage = ProductImage.builder().productImgUrl(uploadFilePath).product(product).build();
            productImageList.add(productImage);
        }
        return productImageList;

    }
}
