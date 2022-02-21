package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
@Slf4j
public class GCEmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MessageSource messageSource;

    @Autowired
    SpringTemplateEngine springTemplateEngine;

    public SimpleMailMessage createSimpleMessage(String from, String to, String subject, String text) {
        log.info("criando mensagem simples");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        return simpleMailMessage;
    }

    public MimeMessage createMimeMessage(String from, String to, String subject, Map<String, String> bodyParam) {
        log.info("Criando mensagem mime");
        MimeMessage mimeMessage;
        try {
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlTemplate = processParams(bodyParam);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(htmlTemplate, true);
            //mimeMessage.setContent(htmlTemplate, "text/html");
        } catch (MessagingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
        return mimeMessage;
    }

    public void send(SimpleMailMessage simpleMailMessage) {
        javaMailSender.send(simpleMailMessage);
    }

    public void send(MimeMessage mimeMessage) {
        javaMailSender.send(mimeMessage);
    }

    public String getSubjectEmail() {
        return messageSource.getMessage("SUBJECT_EMAIL", null, LocaleContextHolder.getLocale());
    }

    public String processParams(Map<String, String> bodyParams) {
        final Context ctx = new Context(LocaleContextHolder.getLocale());
        bodyParams.forEach(ctx::setVariable);
        return springTemplateEngine.process("emailTemplate", ctx);
    }
}
