import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { IFormalDocumentMetadata } from 'app/bespoke/model/formal-document-metadata';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { ITransactionDocument } from 'app/shared/model/transaction-document.model';
import { Observable } from 'rxjs/internal/Observable';
import { ITransactionDocumentMetadata } from 'app/bespoke/model/transaction-document-metadata';
import * as moment from 'moment';

type EntityArrayResponseType = HttpResponse<IFormalDocumentMetadata[]>;
@Injectable({
  providedIn: 'root'
})
export class FormalDocumentMetadataService {
  // TODO Create custom api for pulling all data
  public resourceUrl = SERVER_API_URL + 'api/app/transaction-document/metadata';

  constructor(protected http: HttpClient, private jhiAlertService: JhiAlertService, private log: NGXLogger) {}

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
