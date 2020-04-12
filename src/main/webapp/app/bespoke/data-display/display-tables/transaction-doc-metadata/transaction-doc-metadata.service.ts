import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import * as moment from 'moment';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransactionDocumentMetadata } from 'app/bespoke/model/transaction-document-metadata';
import { ITransactionDocument } from 'app/shared/model/transaction-document.model';
import { IMailAttachmentRequest } from 'app/bespoke/model/mail-attachment-request';

type EntityArrayResponseType = HttpResponse<ITransactionDocumentMetadata[]>;

/**
 * This service pulls data from the transaction-document-metadata resource with null filtering arguments,
 *
 * for now allowing for the data-tables library to have access to all the data at the same time.
 *
 * For further development depending on business demands we might need to develop typical html listings
 *
 * with full blown paging requests. The idea is to be able to share documents selected by the user
 */
@Injectable({
  providedIn: 'root'
})
export class TransactionDocMetadataService {
  public resourceUrl = SERVER_API_URL + '/api/app/transaction-document/metadata';
  public resourceSharingUrl = SERVER_API_URL + '/api/share/transaction-documents';

  constructor(protected http: HttpClient, private jhiAlertService: JhiAlertService, private log: NGXLogger) {}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionDocument[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  /**
   * This method will send an email to the recipient with the attachments
   *
   * @param {string} recipientUsername
   * @param {string} emailRecipient
   * @param {ITransactionDocumentMetadata[]} sharedDocuments
   * @returns {Observable<EntityArrayResponseType>}
   */
  public send(recipientUsername?: string, emailRecipient?: string, sharedDocuments?: ITransactionDocumentMetadata[]): Observable<EntityArrayResponseType>  {
    const mailAttachment: IMailAttachmentRequest = {
      recipientUsername,
      recipientEmail: emailRecipient,
      transactionDocumentMetadata: sharedDocuments
    };

    const copy: IMailAttachmentRequest = this.convertAttachmentDateFromClient(mailAttachment);

    this.log.debug(`Sharing ${mailAttachment.transactionDocumentMetadata?.length} attachments with recipient ${mailAttachment.recipientEmail}`);

    return this.http.post<ITransactionDocumentMetadata[]>(this.resourceSharingUrl, copy, { observe: 'response' });
      // .pipe(map((res: ITransactionDocumentMetadata) => this.convertDateFromServer(res)));
  }

  protected convertAttachmentDateFromClient(mailAttachment: IMailAttachmentRequest): IMailAttachmentRequest {
    if (mailAttachment.transactionDocumentMetadata) {
      mailAttachment.transactionDocumentMetadata.forEach((metadata: ITransactionDocumentMetadata) => {
        metadata.transactionDate = metadata.transactionDate != null ? moment(metadata.transactionDate) : moment();
      });
    }
    return mailAttachment;
  }

  protected convertDateFromServer(res: ITransactionDocumentMetadata): ITransactionDocumentMetadata {
      res.transactionDate = res.transactionDate ? moment(res.transactionDate) : undefined;
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((entity: ITransactionDocumentMetadata) => {
        entity.transactionDate = entity.transactionDate != null ? moment(entity.transactionDate) : moment();
      });
    }
    return res;
  }
}
