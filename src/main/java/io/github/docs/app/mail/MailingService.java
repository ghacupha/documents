package io.github.docs.app.mail;

import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.util.Map;

/**
 * This interface is for sending of emails with document attachments and is designed to do multiple emails for each
 * <p>
 * of which an entry is made into an app with the filename.
 */
public interface MailingService {

    /**
     *
     * @param to
     * @param subject
     * @param content
     * @param isMultipart
     * @param isHtml
     * @param documentAttachments
     */
    @Async
    void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml, Map<String, File> documentAttachments);

    /**
     * TODO dude! get an object for this parameters
     */
    @Async
    void sendAttachmentFromTemplate(String username, String titlePart1, String titlePart2, String email, String templateName, String titleKey, Map<String, File> documentAttachments);

}
