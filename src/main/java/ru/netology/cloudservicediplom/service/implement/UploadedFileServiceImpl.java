package ru.netology.cloudservicediplom.service.implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ru.netology.cloudservicediplom.dto.FileDto;
import ru.netology.cloudservicediplom.exception.CloudServiceDiplomNotFoundException;
import ru.netology.cloudservicediplom.exception.CloudServiceFileException;
import ru.netology.cloudservicediplom.model.Condition;
import ru.netology.cloudservicediplom.model.File;
import ru.netology.cloudservicediplom.repository.UploadedFileRepository;
import ru.netology.cloudservicediplom.repository.UploadedFileUserRepository;
import ru.netology.cloudservicediplom.security.JwtProvider;
import ru.netology.cloudservicediplom.service.UploadedFileService;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static ru.netology.cloudservicediplom.security.JwtProvider.BEARER_LENGTH;


@Service
public class UploadedFileServiceImpl implements UploadedFileService {

    @Value("${data.files.path}")
    private String path;
    private final static String FULL_PATH = "%s\\%s\\";

    private final JwtProvider jwtProvider;
    private final UploadedFileUserRepository repository;
    private final UploadedFileRepository uploadedFileRepository;

    public UploadedFileServiceImpl(JwtProvider jwtProvider, UploadedFileUserRepository repository, UploadedFileRepository uploadedFileRepository) {
        this.jwtProvider = jwtProvider;
        this.repository = repository;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @Autowired

    @PostConstruct
    private void init() {
        var checkPath = Paths.get(path);
        if (!Files.exists(checkPath)) {
            var file = new java.io.File(path);
            file.mkdir();
        }
    }

    @Override
    public List<FileDto> getUploadedFile(String token, int limit) {
        var username = getUsername(token);
        var files = repository.findByUsernameAndCondition(username, Condition.EXISTS);
        return files.stream()
                .limit(limit)
                .map(this::convertFromFile)
                .collect(Collectors.toList());
    }

    @Override
    public java.io.File getFile(String token, String fileName) {
        var username = getUsername(token);
        var fullPath = repository.findByUsernameAndNameAndCondition(username, fileName, Condition.EXISTS)
                .orElseThrow(() -> new CloudServiceDiplomNotFoundException(format("Файл с именем =[%s] не найден.", fileName)))
                .getPath();
        return new java.io.File(fullPath + "//" + fileName);
    }

    @Override
    public void uploadFile(String token, MultipartFile multipartFile, String fileName) {
        var username = getUsername(token);
        var fullPath = format(FULL_PATH, path, username);
        try {
            if (uploadedFileRepository.saveFile(multipartFile, fileName, fullPath)) {
                var now = new Date(System.currentTimeMillis());
                var file = File.builder()
                        .name(fileName)
                        .path(fullPath)
                        .username(username)
                        .size(multipartFile.getBytes().length)
                        .created(now)
                        .updated(now)
                        .condition(Condition.EXISTS)
                        .build();
                repository.save(file);
            } else {
                throw new CloudServiceFileException("Что-то пошло не так с загрузкой файла. Попробуйте снова.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new CloudServiceFileException("Что-то пошло не так с загрузкой файла. Попробуйте снова.");
        }
    }

    @Override
    public void renameFile(String token, String fileName, String newName) {
        var username = getUsername(token);
        var file = repository.findByUsernameAndNameAndCondition(username, fileName, Condition.EXISTS)
                .orElseThrow(() -> new CloudServiceDiplomNotFoundException(format("Файл с именем =[%s] не найден.", fileName)));
        if (uploadedFileRepository.renameFile(fileName, file.getPath(), newName)) {
            file.setName(newName);
            repository.save(file);
        } else {
            throw new CloudServiceFileException("Что-то пошло не так с переименованием.");
        }
    }

    @Override
    public void deleteFile(String token, String fileName) {
        var username = getUsername(token);
        var fullPath = format(FULL_PATH, path, username);
        if (uploadedFileRepository.deleteFile(fileName, fullPath)) {
            var file = repository.findByUsernameAndNameAndCondition(username, fileName, Condition.EXISTS)
                    .orElseThrow(() -> new CloudServiceDiplomNotFoundException(format("Файл с именем =[%s] не найден.", fileName)));
            file.setCondition(Condition.DELETED);
            repository.save(file);
        }

    }

    private String getUsername(String token) {
        return jwtProvider.getUsername(token.substring(BEARER_LENGTH));
    }

    private FileDto convertFromFile(File file) {
        return FileDto.builder()
                .filename(file.getName())
                .size(file.getSize())
                .build();
    }
}
