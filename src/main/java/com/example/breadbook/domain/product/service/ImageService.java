package com.example.breadbook.domain.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    public List<String> upload(MultipartFile[] files);
}
