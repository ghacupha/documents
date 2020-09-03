import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { IFormalDocumentMetadata } from 'app/bespoke/model/formal-document-metadata';
import { SERVER_API_URL } from 'app/app.constants';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { Observable } from 'rxjs/internal/Observable';
import * as moment from 'moment';
import { IMailAttachmentRequest } from 'app/bespoke/model/mail-attachment-request';
import { createRequestOption } from 'app/shared/util/request-util';
import { map } from 'rxjs/operators';

type EntityArrayResponseType = HttpResponse<IFormalDocumentMetadata[]>;
type EntityResponseType = HttpResponse<IFormalDocumentMetadata>;
@Injectable({
  providedIn: 'root'
})
export class FormalDocumentMetadataService {
  public resourceUrl = SERVER_API_URL + '/api/app/formal-document/metadata';
  public resourceSharingUrl = SERVER_API_URL + '/api/share/formal-documents';

  constructor(protected http: HttpClient, private jhiAlertService: JhiAlertService, private log: NGXLogger) {}

  /**
   * This method will send an email to the recipient with the attachments
   *
   * @param {string} recipientUsername
   * @param {string} emailRecipient
   * @param titlePart1
   * @param titlePart2
   * @param {IFormalDocumentMetadata[]} sharedDocuments
   * @param {string} recipientCorrespondent
   * @param {string} brief
   * @returns {Observable<EntityArrayResponseType>}
   */
  public send(
    recipientUsername?: string,
    emailRecipient?: string,
    titlePart1?: string,
    titlePart2?: string,
    sharedDocuments?: IFormalDocumentMetadata[],
    recipientCorrespondent?: string,
    brief?: string
  ): Observable<EntityArrayResponseType> {
    const mailAttachment: IMailAttachmentRequest<IFormalDocumentMetadata> = {
      recipientUsername,
      titlePart1,
      titlePart2,
      recipientEmail: emailRecipient,
      documentMetadata: sharedDocuments,
      recipientCorrespondent,
      brief
    };

    const copy: IMailAttachmentRequest<IFormalDocumentMetadata> = this.convertAttachmentDateFromClient(mailAttachment);

    this.log.debug(`Sharing ${mailAttachment.documentMetadata?.length} attachments with recipient ${mailAttachment.recipientEmail}`);

    return this.http.post<IFormalDocumentMetadata[]>(this.resourceSharingUrl, copy, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFormalDocumentMetadata[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  /**
   * Convert the document-date from client to server-friendly format
   * @param mailAttachment
   * @protected
   */
  protected convertAttachmentDateFromClient(
    mailAttachment: IMailAttachmentRequest<IFormalDocumentMetadata>
  ): IMailAttachmentRequest<IFormalDocumentMetadata> {
    if (mailAttachment.documentMetadata) {
      mailAttachment.documentMetadata.forEach((metadata: IFormalDocumentMetadata) => {
        metadata.documentDate = metadata.documentDate != null ? moment(metadata.documentDate) : moment();
      });
    }
    return mailAttachment;
  }

  /**
   * Convert the document-date attribute to client-visible format
   * @param res
   * @protected
   */
  protected convertDateFromServer(res: IFormalDocumentMetadata): IFormalDocumentMetadata {
    res.documentDate = res.documentDate ? moment(res.documentDate) : undefined;
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((entity: IFormalDocumentMetadata) => {
        entity.documentDate = entity.documentDate != null ? moment(entity.documentDate) : moment();
      });
    }
    return res;
  }
}
