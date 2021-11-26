package repository;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UploadedFileRepository {

    boolean saveFile(MultipartFile file, String fileName, String path) throws IOException;

    boolean renameFile(String fileName, String path, String newName);

    boolean deleteFile(String fileName, String path);
}
