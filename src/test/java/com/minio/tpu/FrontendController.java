package com.minio.tpu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.List;

@RestController
public class FrontendController {

    @Autowired
    private MinioService backendService;

    @GetMapping("/buckets")
    public ResponseEntity<List<String>> getBuckets() throws Exception {
        List<String> buckets = backendService.listBuckets();
        return ResponseEntity.ok(buckets);
    }

    @PostMapping("/buckets")
    public ResponseEntity<String> createBucket(@RequestParam String bucketName) throws Exception {
        backendService.createBucket(bucketName);
        return ResponseEntity.ok("Bucket created successfully");
    }

    @GetMapping("/buckets/{bucketName}")
    public ResponseEntity<List<String>> getFilesInBucket(@RequestParam String bucketName) throws Exception {
        List<String> files = backendService.listFilesInBucket(bucketName);
        return ResponseEntity.ok(files);
    }

    @PostMapping("/buckets/{bucketName}/upload")
    public ResponseEntity<String> uploadFile(@RequestParam String bucketName, @RequestParam MultipartFile file) {
        try {
            // Вычисляем хэш файла
            String fileHash = calculateHash(file.getInputStream());

            // Проверяем хэш файла с бэкендом
            boolean hashMatches = backendService.verifyHash(bucketName, file.getOriginalFilename(), fileHash);

            if (hashMatches) {
                backendService.uploadFile(bucketName, file);
                return ResponseEntity.ok("File uploaded successfully");
            } else {
                return ResponseEntity.badRequest().body("File hash does not match");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading file");
        }
    }

    private String calculateHash(InputStream inputStream) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[8192];
        int read = 0;
        while ((read = inputStream.read(buffer)) > 0) {
            digest.update(buffer, 0, read);
        }
        byte[] hash = digest.digest();

        StringBuilder hashHex = new StringBuilder();
        for (byte b : hash) {
            hashHex.append(String.format("%02x", b));
        }

        return hashHex.toString();
    }
}
