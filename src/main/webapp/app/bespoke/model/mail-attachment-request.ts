import { ITransactionDocumentMetadata } from 'app/bespoke/model/transaction-document-metadata';

export interface IMailAttachmentRequest {
  recipientEmail?: string;
  transactionDocumentMetadata?: ITransactionDocumentMetadata[];
}

export class MailAttachmentRequest implements IMailAttachmentRequest {
  constructor(public recipientEmail?: string, public transactionNumber?: ITransactionDocumentMetadata[]) {}
}
