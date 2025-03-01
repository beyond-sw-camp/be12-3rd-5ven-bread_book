package com.example.breadbook.domain.product.servie;

import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.model.ProductImage;
import com.example.breadbook.domain.product.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public void createProductImage(List<String> uploadFilePaths, Product product) {
        for (String uploadFilePath : uploadFilePaths) {
            productImageRepository.save(ProductImage.builder()
                                    .productImgUrl(uploadFilePath)
                                    .product(product)
                                    .build());
        }
    }
}
