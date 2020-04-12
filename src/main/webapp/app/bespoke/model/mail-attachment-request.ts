import { ITransactionDocumentMetadata } from 'app/bespoke/model/transaction-document-metadata';

export interface IMailAttachmentRequest {
  recipientUsername?: string;
  titlePart1?: string;
  titlePart2?: string;
  recipientEmail?: string;
  transactionDocumentMetadata?: ITransactionDocumentMetadata[];
}

export class MailAttachmentRequest implements IMailAttachmentRequest {
  constructor(
    public recipientUsername?: string,
    public titlePart1?: string,
    public titlePart2?: string,
    public recipientEmail?: string,
    public transactionNumber?: ITransactionDocumentMetadata[]
  ) {}
}
