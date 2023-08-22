package com.minio.tpu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
public class MinioController {

    @PostMapping("/buckets/{bucketName}/upload")
    public ResponseEntity<String> uploadFile(
            @PathVariable String bucketName,
            @RequestParam MultipartFile file,
            @RequestParam String appId, // Идентификатор приложения
            @RequestParam String accessKeyHash // Хэш ключа доступа
    ) {
        try {
            // Проверьте хэш ключа доступа перед загрузкой файла
            if (!minioService.verifyAccessKeyHash(appId, accessKeyHash)) {
                throw new RuntimeException("Ошибка аутентификации: неверный ключ доступа");
            }

            minioService.uploadFile(bucketName, file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private MinioService minioService;

    // Получение списка корзин
    @GetMapping("/buckets")
    public ResponseEntity<List<String>> getBuckets() {
        List<String> buckets = null;
        try {
            buckets = minioService.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении списка корзин: " + e.getMessage(), e);
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
            throw new RuntimeException("Ошибка при получении списка файлов в корзине: " + e.getMessage(), e);
        }
        return ResponseEntity.ok(files);
    }

    // Создание корзины
    @PostMapping("/buckets")
    public ResponseEntity<String> createBucket(@RequestParam String bucketName) {
        try {
            minioService.createBucket(bucketName);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании корзины: " + e.getMessage(), e);
        }
        return ResponseEntity.ok("Корзина успешно создана");
    }

    // Загрузка файла в корзину
    @PostMapping("/buckets/{bucketName}/upload")
    public ResponseEntity<String> uploadFile(@PathVariable String bucketName, @RequestParam MultipartFile file) {
        try {
            minioService.uploadFile(bucketName, file);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке файла в корзину: " + e.getMessage(), e);
        }
        return ResponseEntity.ok("Файл успешно загружен");
    }

    // Удаление файла из корзины
    @DeleteMapping("/buckets/{bucketName}/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String bucketName, @PathVariable String fileName) {
        try {
            minioService.deleteFile(bucketName, fileName);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении файла из корзины: " + e.getMessage(), e);
        }
        return ResponseEntity.ok("Файл успешно удален");
    }

    @GetMapping("/buckets/{bucketName}/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String bucketName,
            @PathVariable String fileName,
            @RequestParam String hash
    ) {
        try {
            if (!minioService.verifyHash(bucketName, fileName, hash)) {
                throw new RuntimeException("Ошибка хэширования: хэш загруженного файла не соответствует исходному хэшу");
            }

            InputStreamResource resource = minioService.downloadFile(bucketName, fileName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
