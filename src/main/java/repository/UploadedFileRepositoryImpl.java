package repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class UploadedFileRepositoryImpl implements UploadedFileRepository{

    @Override
    public boolean saveFile(MultipartFile file, String fileName, String path) {
        return false;
    }

    @Override
    public boolean renameFile(String fileName, String path, String newName) {
        return false;
    }

    @Override
    public boolean deleteFile(String fileName, String path) {
        return false;
    }
}
