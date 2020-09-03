import { ITransactionDocumentMetadata } from 'app/bespoke/model/transaction-document-metadata';
import { IFormalDocumentMetadata } from 'app/bespoke/model/formal-document-metadata';

/**
 * This Object contains the attachment information and the details for sharing them.
 */
export interface IMailAttachmentRequest<T> {
  recipientUsername?: string;
  titlePart1?: string;
  titlePart2?: string;
  recipientEmail?: string;
  documentMetadata?: T[];
  recipientCorrespondent?: string;
  brief?: string;
}

export class TransactionsMailAttachmentRequest implements IMailAttachmentRequest<ITransactionDocumentMetadata> {
  constructor(
    public recipientUsername?: string,
    public titlePart1?: string,
    public titlePart2?: string,
    public recipientEmail?: string,
    public documentMetadata?: ITransactionDocumentMetadata[],
    public recipientCorrespondent?: string,
    public brief?: string
  ) {}
}

export class FormalMailAttachmentRequest implements IMailAttachmentRequest<IFormalDocumentMetadata> {
  constructor(
    public recipientUsername?: string,
    public titlePart1?: string,
    public titlePart2?: string,
    public recipientEmail?: string,
    public documentMetadata?: IFormalDocumentMetadata[],
    public recipientCorrespondent?: string,
    public brief?: string
  ) {}
}
