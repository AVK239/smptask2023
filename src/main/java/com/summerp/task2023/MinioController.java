package com.summerp.task2023;

import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class MinioController {

    @Autowired
    private MinioService minioService;

    // Получение списка корзин
    @GetMapping("/buckets")
    public ResponseEntity<List<String>> getBuckets() {
        List<String> buckets = null;
        try {
            buckets = minioService.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(buckets);
    }

    // Получение списка файлов в корзине
    @GetMapping("/buckets/{bucketName}")
    public ResponseEntity<List<String>> getFilesInBucket(@PathVariable String bucketName) {
        List<String> files = null;
        try {
            files = minioService.listFilesInBucket(bucketName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(files);
    }

    // Создание корзины
    @PostMapping("/buckets")
    public ResponseEntity<String> createBucket(@RequestParam String bucketName) {
        try {
            minioService.createBucket(bucketName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Bucket created successfully");
    }

    // Загрузка файла в корзину
    @PostMapping("/buckets/{bucketName}/upload")
    public ResponseEntity<String> uploadFile(@PathVariable String bucketName, @RequestParam MultipartFile file) {
        try {
            minioService.uploadFile(bucketName, file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("File uploaded successfully");
    }

    // Удаление файла из корзины
    @DeleteMapping("/buckets/{bucketName}/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String bucketName, @PathVariable String fileName) {
        try {
            minioService.deleteFile(bucketName, fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("File deleted successfully");
    }
}
