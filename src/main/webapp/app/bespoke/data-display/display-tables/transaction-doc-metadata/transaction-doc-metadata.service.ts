import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ITransactionDocumentMetadata } from 'app/bespoke/model/ITransactionDocumentMetadata';
import * as moment from 'moment';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransactionDocument } from 'app/shared/model/transaction-document.model';

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
  // TODO Create custom api for pulling all data
  public resourceUrl = SERVER_API_URL + 'api/app/transaction-document/metadata';

  constructor(protected http: HttpClient, private jhiAlertService: JhiAlertService, private log: NGXLogger) {}

  // getTransactionMetadata(): Observable<EntityArrayResponseType> {
  //   return (
  //     this.http
  //       .get<ITransactionDocumentMetadata[]>(this.resourceUrl, { observe: 'response' })
  //       // .pipe(
  //       //   tap((res: EntityArrayResponseType) => this.log.info(`fetched : ${res.body.length} deposit-account items`)),
  //       // )
  //       .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)))
  //   );
  // }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionDocument[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
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
