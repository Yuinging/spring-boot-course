package top.yyyin.boot.config.comtroller;


import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.yyyin.boot.config.service.OssService;

@RestController
@RequestMapping("/oss")
public class OssController {
    @Resource
    private OssService ossService;

    @PostMapping("upload")
    public String upload(MultipartFile file) {
        return ossService.upload(file);
    }
}
