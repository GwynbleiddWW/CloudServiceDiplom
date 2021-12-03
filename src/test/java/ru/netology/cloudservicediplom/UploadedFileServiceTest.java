package ru.netology.cloudservicediplom;

import exception.CloudServiceDiplomNotFoundException;
import exception.CloudServiceFileException;
import model.Condition;
import model.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.UploadedFileRepository;
import repository.UploadedFileUserRepository;
import security.JwtProvider;
import service.UploadedFileService;
import service.implement.UploadedFileServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static security.JwtProvider.BEARER_LENGTH;

public class UploadedFileServiceTest {

    private static UploadedFileService cloudService;

    private static final String TOKEN = "a1Y7zzBs123";
    private static final String USER = "User";
    private static final Condition EXISTS = Condition.EXISTS;
    private static final Date DATE = new Date(System.currentTimeMillis());
    private static final File FILE = new File(1L, DATE, DATE, EXISTS, "testFile", USER, "path", 100L);
    private static final List<File> FILE_LIST = List.of(
            new File(1L, DATE, DATE, EXISTS, "Jhon", USER, "path", 100L),
            new File(2L, DATE, DATE, EXISTS, "Bill", USER, "path", 150L)
    );

    @BeforeAll
    public static void init() {
        var fileRepository = Mockito.mock(UploadedFileUserRepository.class);
        var jwtTokenProvider = Mockito.mock(JwtProvider.class);
        var fileLocalRepository = Mockito.mock(UploadedFileRepository.class);
        Mockito.when(fileRepository.findByUsernameAndCondition(USER, EXISTS))
                .thenReturn(FILE_LIST);
        Mockito.when(jwtTokenProvider.getUsername(TOKEN.substring(BEARER_LENGTH))).thenReturn(USER);
        Mockito.when(fileRepository.findByUsernameAndNameAndCondition(USER, "testFile", EXISTS))
                .thenReturn(Optional.of(FILE));
        cloudService = new UploadedFileServiceImpl(jwtTokenProvider, fileRepository, fileLocalRepository);
    }

    @Test
    void whenGetFilesThenReturnsList() {
        var filesDTO = cloudService.getUploadedFile(TOKEN, 10);
        assertEquals(filesDTO.size(), 2);
        assertEquals("Jhon", filesDTO.get(0).getFilename());
        assertEquals("Bill", filesDTO.get(1).getFilename());
    }

    @Test
    void whenGetFileThenReturnFileWithRightProp() {
        var javaFile = cloudService.getFile(TOKEN, "testFile");
        assertEquals("testFile", javaFile.getName());
        assertEquals("path\\testFile", javaFile.getPath());
    }

    @Test
    void whenRenameFileThenExceptionThrows() {
        Throwable thrown = assertThrows(CloudServiceDiplomNotFoundException.class, () -> cloudService.renameFile(TOKEN, "", ""));
        assertNotNull(thrown.getMessage());
    }

    @Test
    void whenUploadFileThenExceptionThrows() {
        Throwable thrown = assertThrows(CloudServiceFileException.class, () -> cloudService.uploadFile(TOKEN, null, ""));
        assertNotNull(thrown.getMessage());
    }
}
