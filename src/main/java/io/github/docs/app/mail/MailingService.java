package io.github.docs.app.mail;

import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.util.Map;

/**
 * This interface is for sending of emails with document attachments and is designed to do multiple emails for each
 *
 * of which an entry is made into an app with the filename.
 */
public interface MailingService {
    @Async
    void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml, Map<String,File> documentAttachments);

    @Async
    void sendAttachmentFromTemplate(String email, String templateName, String titleKey, Map<String,File> documentAttachments);

}
