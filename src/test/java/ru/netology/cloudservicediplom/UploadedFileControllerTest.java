package ru.netology.cloudservicediplom;


import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ru.netology.cloudservicediplom.dto.FileDto;
import ru.netology.cloudservicediplom.restController.UploadedFileController;
import ru.netology.cloudservicediplom.security.JwtProvider;
import ru.netology.cloudservicediplom.service.UploadedFileService;


import javax.annotation.Resource;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UploadedFileController.class)
public class UploadedFileControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UploadedFileService service;
    
    @MockBean
    JwtProvider jwtProvider;

    @Value("classpath:files\\testFile.txt")
    static Resource resourceFile;

    private static final String token = "Bearer token";
    private static final String fileName = "testFile.txt";

    @Test
    @SneakyThrows
    public void whenGetFileThenStatusOk() {
        var file = new ClassPathResource("files\\testFile.txt").getFile();
        when(service.getFile(token, fileName)).thenReturn(file);

        mockMvc.perform(get("/file").header("auth-token", token).param("filename", fileName))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void whenGetListThenStatusOk() {
        var list = List.of(
                FileDto.builder().filename("name").size(100L).build()
        );
        when(service.getUploadedFile(token, 1)).thenReturn(list);
        mockMvc.perform(get("/list").header("auth-token", token).param("limit", "1"))
                .andExpect(status().isOk());
    }
}
