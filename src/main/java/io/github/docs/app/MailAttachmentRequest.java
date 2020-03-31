package io.github.docs.app;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * This is a simple request object containing parameters for calls requesting to share
 *
 * emails with attachments
 *
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MailAttachmentRequest {

    @NotNull
    private String recipientEmail;
}
