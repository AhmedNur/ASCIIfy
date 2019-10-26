package ASCIIfy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
    @Autowired
    ASCIIService asciiService;

    @PostMapping("/upload")
    public ResponseEntity<String> convertImage(@RequestParam("file")MultipartFile file) {
        return new ResponseEntity<>(asciiService.ASCIIfy(file), HttpStatus.OK);
    }
}
