package top.yyyin.boot.config.service;

import org.springframework.web.multipart.MultipartFile;
import top.yyyin.boot.config.enums.ResultStatus;
import top.yyyin.boot.config.moddel.Mail;

public interface MailService {
    /**
     * 发送简单邮件
     *
     * @param mail 邮件信息
     */
    ResultStatus sendSimpleMail(Mail mail);

    /**
     * 发送简单邮件
     *
     * @param mail 邮件信息
     */
    ResultStatus sendHtmlMail(Mail mail);

    /**
     * 发送带附件的邮件
     *
     * @param mail  邮件信息
     * @param files 附件文件
     */
    ResultStatus sendAttachmentsMail(Mail mail, MultipartFile[]files );
}


