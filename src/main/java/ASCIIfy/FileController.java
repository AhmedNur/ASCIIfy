package ASCIIfy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @Autowired
    ASCIIService as;

    @GetMapping("/")
    public String index(){
        return "<form method=\"POST\" action=\"/upload\" enctype=\"multipart/form-data\">\n" +
                "    <input type=\"file\" name=\"file\" /><br/><br/>\n" +
                "    <input type=\"submit\" value=\"Submit\" />\n" +
                "</form>";
    }

    @PostMapping("/upload")
    public String asciiImageUpload(@RequestParam("file")MultipartFile file)
    {
        return as.ASCIIfy(file);
    }
}
