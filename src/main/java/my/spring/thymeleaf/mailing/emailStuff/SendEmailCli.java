package my.spring.thymeleaf.mailing.emailStuff;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Collections;


@Component
@Slf4j
public class SendEmailCli implements CommandLineRunner {

    private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "html/registrationEmail";
    private final String RECEPIENT = "RECEPIENT@domain.com";

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;


    JavaMailSender javaMailSender;
    TemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(2));
        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setPrefix("/templates/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    public void sendHtmlMessage() throws MessagingException {



        log.info("username = {}", username);
        log.info("password = {}", password);

        Context ctx = new Context();
        ctx.setVariable("confirmationLink", "https://yandex.ru"); //this var send to template via context

        templateEngine.addTemplateResolver(htmlTemplateResolver());
        String htmlContent = templateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

        message.setSubject("Example HTML email (simple)");
        message.setFrom(username);
        message.setTo(RECEPIENT);
        message.setText(htmlContent, true);

        javaMailSender.send(message.getMimeMessage());
    }

    public void sendTxtMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(RECEPIENT);
        message.setSubject("Test message from Spring boot app");
        message.setText("Test text\n Spring boot app is working!");
        log.info("message1T = {}", message);
        javaMailSender.send(message);
    }


    @Override
    public void run(String... args) throws Exception {
        sendHtmlMessage();
    }

}
