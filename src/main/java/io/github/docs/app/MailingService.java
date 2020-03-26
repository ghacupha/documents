package io.github.docs.app;

import io.github.docs.domain.User;
import org.springframework.scheduling.annotation.Async;

import java.io.File;

public interface MailingService {
    @Async
    void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml, String attachmentName, File documentAttachment);

    @Async
    void sendEmailFromTemplate(User user, String templateName, String titleKey, String attachmentName, File documentAttachment);
}
