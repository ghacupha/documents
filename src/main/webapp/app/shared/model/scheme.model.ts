import { ITransactionDocument } from 'app/shared/model/transaction-document.model';
import { IFormalDocument } from 'app/shared/model/formal-document.model';

export interface IScheme {
  id?: number;
  schemeName?: string;
  schemeCode?: string;
  schemeDescription?: string;
  transactionDocuments?: ITransactionDocument[];
  formalDocuments?: IFormalDocument[];
}

export class Scheme implements IScheme {
  constructor(
    public id?: number,
    public schemeName?: string,
    public schemeCode?: string,
    public schemeDescription?: string,
    public transactionDocuments?: ITransactionDocument[],
    public formalDocuments?: IFormalDocument[]
  ) {}
}
