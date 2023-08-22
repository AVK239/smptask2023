package com.minio.tpu;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MinioService {

    public boolean verifyAccessKeyHash(String appId, String providedHash) {
        // Получите хэш ключа доступа для указанного appId из вашей базы данных или хранилища
        String storedHash = getStoredHashForAppId(appId);

        // Сравните хэши и верните результат
        return storedHash.equals(providedHash);
    }

    private String getStoredHashForAppId(String appId) {
        // Здесь вы можете использовать ваш механизм хранения данных (например, базу данных)
        // для получения хэша ключа доступа по appId
        // Замените этот пример на реальный код получения хэша
        return "stored_hash_for_" + appId;
    }

    public String generateAccessKeyHash(String accessKey) {
        return DigestUtils.sha256Hex(accessKey);
    }

    public boolean verifyHash(String bucketName, String fileName, String hashToCheck) throws Exception {
        InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );

        String downloadedHash = DigestUtils.sha256Hex(inputStream);
        return downloadedHash.equals(hashToCheck);
    }


    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    public List<String> listBuckets() throws Exception {
        return minioClient.listBuckets().stream().map(Bucket::name).collect(Collectors.toList());
    }

    public List<String> listFilesInBucket(String bucketName) throws Exception {
        List<String> fileNames = new ArrayList<>();

        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        Iterator<Result<Item>> iterator = results.iterator();

        while (iterator.hasNext()) {
            Result<Item> result = iterator.next();
            fileNames.add(result.get().objectName());
        }

        return fileNames;
    }

    public void createBucket(String bucketName) throws Exception {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
    }

    public void uploadFile(String bucketName, MultipartFile file) throws Exception {
        ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(file.getOriginalFilename())
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());

        // Handle response if needed
    }

    public void deleteFile(String bucketName, String fileName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build());
    }

    public InputStreamResource downloadFile(String bucketName, String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );

        // Обернуть InputStream в InputStreamResource
        return new InputStreamResource(inputStream);
    }
}
