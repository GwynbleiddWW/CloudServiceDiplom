package restController;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UploadedFileService;

import java.io.File;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/")
public class UploadedFileController {

    private final UploadedFileService uploadedFileService;

    @GetMapping("/list")
    public List<File> getAllUploadedFile() {
        return null;
    }

    @PostMapping("/file")
    public ResponseEntity<?> upload() {
        return null;
    }


    @GetMapping("/file")
    public ResponseEntity<?> download() {
        return null;
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> delete() {
        return null;
    }
}
