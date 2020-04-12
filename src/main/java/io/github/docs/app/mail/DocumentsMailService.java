package io.github.docs.app.mail;

import io.github.jhipster.config.JHipsterProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

/**
 * Service for sending emails.
 * <p>
 * This implementation is similar to the default user accounting email service though it exposes api for attaching documents which are to be sent as attachments
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Slf4j
@Service
public class DocumentsMailService implements MailingService {

    private static final String USER = "addressee";

    private static final String SENDER = "documentsUser";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public DocumentsMailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml,  Map<String,File> documentAttachments) {

        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}", isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            messageHelper.setTo(to);
            messageHelper.setFrom(jHipsterProperties.getMail().getFrom());
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);

            // Add attachments here
            documentAttachments.forEach((filename, file) -> {
                // todo add attachments to zip and add attachments
                try {
                    messageHelper.addAttachment(filename, file);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });

            // todo messageHelper.addAttachment(fileZipper(subject + ".zip", documentAttachments));

            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }


    @Override
    @Async
    public void sendAttachmentFromTemplate(String username, String email, String templateName, String titleKey, Map<String,File> documentAttachments) {

        Context context = new Context();

        context.setVariable(USER, username == null? "Recipient" : username);

        // todo obtain login id from the server
        context.setVariable(SENDER, "Sky Walker");
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);

        // todo obtain title parts from client request
        // Specify title part 1 & 2
        String[] titleParts = {"Documents Shared", "Documents Automation Project"};

        String subject = messageSource.getMessage(titleKey, titleParts, Locale.ENGLISH);
        sendEmail(email, subject, content, true, true,documentAttachments);
    }

}
