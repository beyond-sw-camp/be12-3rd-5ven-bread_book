package com.example.breadbook.domain.product.servie;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocalImageService implements ImageService {
    @Value("${UPLOAD_PATH}")
    private String defaultUploadPath;

    private String makeDir() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String uploadPath = defaultUploadPath + "/" + date;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return "/" + date;
    }

    @Override
    public List<String> upload(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return new ArrayList<>(); // 파일이 없으면 빈 리스트 반환
        }

        List<String> uploadFilePaths = new ArrayList<>();
        String uploadPath = makeDir();
        for (MultipartFile file : files) {
            String originFilename = file.getOriginalFilename();

            String uploadFilePath = uploadPath + "/" + UUID.randomUUID().toString() + "_" + originFilename;
            uploadFilePaths.add(uploadFilePath);

            File uploadFile = new File(defaultUploadPath+"/"+uploadFilePath);
            try {
                file.transferTo(uploadFile);
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패: "+ originFilename, e);
            }
        }
        return uploadFilePaths;
    }

}
