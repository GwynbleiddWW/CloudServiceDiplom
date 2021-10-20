package service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import repository.UploadedFileRepository;

import java.io.File;
import java.util.List;

@AllArgsConstructor
@Service
public class UploadedFileServiceImpl implements UploadedFileService{

    private final UploadedFileRepository uploadedFileRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    public List<File> getUploadedFile(String filename) {
        return null;
    }

    @Override
    public void uploadFile(String token, MultipartFile file, String fileName) {

    }

    @Override
    public void renameFile(String token, String fileName, String newName) {

    }

    @Override
    public void deleteFile(String token, String fileName) {

    }

    @Override
    public void downloadFileFromCloud(String filename) {

    }
}
