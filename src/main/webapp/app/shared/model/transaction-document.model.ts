import { Moment } from 'moment';
import { IScheme } from 'app/shared/model/scheme.model';

export interface ITransactionDocument {
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
  transactionAttachmentContentType?: string;
  transactionAttachment?: any;
  filename?: string;
  schemes?: IScheme[];
}

export class TransactionDocument implements ITransactionDocument {
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
    public transactionAttachmentContentType?: string,
    public transactionAttachment?: any,
    public filename?: string,
    public schemes?: IScheme[]
  ) {}
}
