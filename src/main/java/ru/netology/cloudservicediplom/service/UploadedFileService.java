package ru.netology.cloudservicediplom.service;


import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudservicediplom.dto.FileDto;

import java.io.File;
import java.util.List;


public interface UploadedFileService {

    List<FileDto> getUploadedFile(String token, int limit);

    File getFile(String token, String fileName);

    void uploadFile(String token, MultipartFile file, String fileName);

    void renameFile(String token, String fileName, String newName);

    void deleteFile(String token, String fileName);
}
