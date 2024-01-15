package br.com.proccedure.unicasu.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String body) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            emailSender.send(message);
        }
        catch (MailException e){
            throw new EmailException("Failed to send email");
        }
    }
}

/*
import freemarker.template.Configuration;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String token) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("unicasistemabr@gmail.com");
            message.setTo("rubens_m.rodrigues@hotmail.com");
            message.setSubject("titulo do email");
            message.setText("message");
            emailSender.send(message);

        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    public void enviar(String destinatario, Map<String, Object> propriedades) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("Unica — Recuperação de Senha");
            mimeMessageHelper.setFrom("unicasistemabr@gmail.com");
            mimeMessageHelper.setTo(destinatario);
            mimeMessageHelper.setText(getContentFromTemplate(propriedades), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String getContentFromTemplate(Map<String, Object> model) {
        StringBuffer content = new StringBuffer();
        try {
            Configuration fmConfiguration = new Configuration(Configuration.getVersion());
            fmConfiguration.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "/templates");
            content.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(fmConfiguration.getTemplate("recupera-senha-template.flth"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
*/



