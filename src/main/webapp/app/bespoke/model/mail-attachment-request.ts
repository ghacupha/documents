import { ITransactionDocumentMetadata } from 'app/bespoke/model/transaction-document-metadata';

/**
 * This Object contains the attachment information and the details for sharing them.
 */
export interface IMailAttachmentRequest {
  recipientUsername?: string;
  titlePart1?: string;
  titlePart2?: string;
  recipientEmail?: string;
  transactionDocumentMetadata?: ITransactionDocumentMetadata[];
  recipientCorrespondent?: string;
  brief?: string;
}

export class MailAttachmentRequest implements IMailAttachmentRequest {
  constructor(
    public recipientUsername?: string,
    public titlePart1?: string,
    public titlePart2?: string,
    public recipientEmail?: string,
    public transactionDocumentMetadata?: ITransactionDocumentMetadata[],
    public recipientCorrespondent?: string,
    public brief?: string
  ) {}
}
