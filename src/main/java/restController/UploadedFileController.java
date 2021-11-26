package restController;

import dto.FileDto;
import dto.RenameFileDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.UploadedFileService;

import javax.validation.constraints.Min;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/")
public class UploadedFileController {

    private final UploadedFileService uploadedFileService;

    @GetMapping("/list")
    public List<FileDto> getAllFiles(@RequestHeader("auth-token") String authToken,
                                     @RequestParam("limit") @Min(1) int limit) {

        return uploadedFileService.getUploadedFile(authToken, limit);
    }

    @GetMapping("/file")
    @SneakyThrows
    public ResponseEntity<byte[]> download(@RequestHeader("auth-token") String authToken,
                                           @RequestParam("filename") String fileName) {
        var file = uploadedFileService.getFile(authToken, fileName);
        Path path = Paths.get(file.getAbsolutePath());
        var bytes = Files.readAllBytes(path);
        var probeContentType = Files.probeContentType(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(file.getName()).build().toString())
                .contentType(probeContentType != null ? MediaType.valueOf(probeContentType) : MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }

    @PutMapping("/file")
    public ResponseEntity<?> rename(@RequestHeader("auth-token") String authToken,
                                    @RequestParam("filename") String fileName,
                                    @RequestBody RenameFileDto renameFile) {
        uploadedFileService.renameFile(authToken, fileName, renameFile.getNewName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity<?> upload(@RequestHeader("auth-token") String authToken,
                                    @RequestParam("filename") String fileName, MultipartFile file) {
        uploadedFileService.uploadFile(authToken, file, fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> delete(@RequestHeader("auth-token") String authToken,
                                    @RequestParam("filename") String fileName) {
        uploadedFileService.deleteFile(authToken, fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
