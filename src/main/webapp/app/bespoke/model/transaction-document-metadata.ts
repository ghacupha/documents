import { Moment } from 'moment';

export interface ITransactionDocumentMetadata {
  id?: number;
  transactionNumber?: string;
  transactionDate?: Moment;
  briefDescription?: string;
  justification?: string;
  transactionAmount?: number;
  payeeName?: string;
  invoiceNumber?: string;
  lpoNumber?: string;
  debitNoteNumber?: string;
  logisticReferenceNumber?: string;
  memoNumber?: string;
  documentStandardNumber?: string;
  filename?: string;
}

export class TransactionDocumentMetadata implements ITransactionDocumentMetadata {
  constructor(
    public id?: number,
    public transactionNumber?: string,
    public transactionDate?: Moment,
    public briefDescription?: string,
    public justification?: string,
    public transactionAmount?: number,
    public payeeName?: string,
    public invoiceNumber?: string,
    public lpoNumber?: string,
    public debitNoteNumber?: string,
    public logisticReferenceNumber?: string,
    public memoNumber?: string,
    public documentStandardNumber?: string,
    public filename?: string
  ) {}
}
