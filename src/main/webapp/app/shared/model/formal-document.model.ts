import { Moment } from 'moment';
import { IUserProfile } from 'app/shared/model/user-profile.model';
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
  documentOwners?: IUserProfile[];
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
    public documentOwners?: IUserProfile[]
  ) {}
}
