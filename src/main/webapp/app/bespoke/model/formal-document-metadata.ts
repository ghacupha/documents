import { Moment } from 'moment';
import { IScheme } from 'app/shared/model/scheme.model';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';

export interface IFormalDocumentMetadata {
  id?: number;
  documentTitle?: string;
  documentSubject?: string;
  briefDescription?: string;
  documentDate?: Moment;
  documentType?: DocumentType;
  documentStandardNumber?: string;
  filename?: string;
  schemes?: IScheme[];
}

export class FormalDocumentMetadata implements IFormalDocumentMetadata {
  constructor(
    public id?: number,
    public documentTitle?: string,
    public documentSubject?: string,
    public briefDescription?: string,
    public documentDate?: Moment,
    public documentType?: DocumentType,
    public documentStandardNumber?: string,
    public filename?: string,
    public schemes?: IScheme[]
  ) {}
}
