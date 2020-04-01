import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ITransactionDocument } from 'app/shared/model/transaction-document.model';
import * as moment from 'moment';

type EntityArrayResponseType = HttpResponse<ITransactionDocument[]>;

@Injectable({
  providedIn: 'root'
})
export class TransactionDocMetadataService {
  // TODO Create custom api for pulling all data
  public resourceUrl = SERVER_API_URL + '/services/depositanalysismain/api/app/list/deposit-accounts';

  constructor(protected http: HttpClient, private jhiAlertService: JhiAlertService, private log: NGXLogger) {}

  getTransactionMetadata(): Observable<EntityArrayResponseType> {
    this.log.info(`Pulling data for all deposit accounts...`);

    return (
      this.http
        .get<ITransactionDocument[]>(this.resourceUrl, { observe: 'response' })
        // .pipe(
        //   tap((res: EntityArrayResponseType) => this.log.info(`fetched : ${res.body.length} deposit-account items`)),
        // )
        .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)))
    );
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((entity: ITransactionDocument) => {
        entity.transactionDate = entity.transactionDate != null ? moment(entity.transactionDate) : moment();
      });
    }
    return res;
  }
}
