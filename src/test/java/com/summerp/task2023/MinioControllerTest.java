package com.summerp.task2023;

import com.summerp.task2023.MinioController;
import io.minio.MinioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@SpringBootTest
public class MinioControllerTest {

    @Mock
    private MinioClient minioClient;

    @InjectMocks
    private MinioController minioController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(minioController).build();
    }

    @Test
    public void testGetBuckets() throws Exception {
        // Логика для тестирования метода getBuckets
    }

    @Test
    public void testCreateBucket() throws Exception {
        // Логика для тестирования метода createBucket
    }

    @Test
    public void testGetFilesInBucket() throws Exception {
        // Логика для тестирования метода getFilesInBucket
    }

    @Test
    public void testUploadFile() throws Exception {
        // Логика для тестирования метода uploadFile
    }

    @Test
    public void testDeleteFile() throws Exception {
        // Логика для тестирования метода deleteFile
    }
}
