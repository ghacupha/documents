import { Moment } from 'moment';
import { IScheme } from 'app/shared/model/scheme.model';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';

export interface IFormalDocument {
  id?: number;
  documentTitle?: string;
  documentSubject?: string;
  briefDescription?: string;
  documentDate?: Moment;
  documentType?: DocumentType;
  documentStandardNumber?: string;
  documentAttachmentContentType?: string;
  documentAttachment?: any;
  schemes?: IScheme[];
}

export class FormalDocument implements IFormalDocument {
  constructor(
    public id?: number,
    public documentTitle?: string,
    public documentSubject?: string,
    public briefDescription?: string,
    public documentDate?: Moment,
    public documentType?: DocumentType,
    public documentStandardNumber?: string,
    public documentAttachmentContentType?: string,
    public documentAttachment?: any,
    public schemes?: IScheme[]
  ) {}
}
