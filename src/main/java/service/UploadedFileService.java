package service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface UploadedFileService {

    List<File> getUploadedFile(String filename);

    void uploadFile(String token, MultipartFile file, String fileName);

    void renameFile(String token, String fileName, String newName);

    void deleteFile(String token, String fileName);

    void downloadFileFromCloud (String filename);
}
