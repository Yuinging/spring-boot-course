package top.yyyin.boot.config.comtroller;


import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {
    @Value("${custom.qrcode.content}")
    private String content;

//    @GetMapping("/generate")
//    public void generateQrCode(){
//        // 生成指定url对应的二维码到文件，宽和高都是300像素
//        QrCodeUtil.generate(content, 300, 300, FileUtil.file("qrcode.jpg"));
//
//    }
    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateQrCode(){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        QrCodeUtil.generate(content, 300, 300, ImgUtil.IMAGE_TYPE_PNG, out);
        byte[] bytes = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
