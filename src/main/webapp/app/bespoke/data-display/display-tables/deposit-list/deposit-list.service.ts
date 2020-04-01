import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { Observable } from 'rxjs/internal/Observable';
import { IDepositAccount } from 'app/shared/model/depositAnalysisMain/deposit-account.model';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

type EntityArrayResponseType = HttpResponse<IDepositAccount[]>;

@Injectable({
  providedIn: 'root'
})
export class DepositListService {
  // TODO Create custom api for pulling all data
  public resourceUrl = SERVER_API_URL + '/services/depositanalysismain/api/app/list/deposit-accounts';

  constructor(protected http: HttpClient, private jhiAlertService: JhiAlertService, private log: NGXLogger) {}

  getDeposits(): Observable<EntityArrayResponseType> {
    this.log.info(`Pulling data for all deposit accounts...`);

    return (
      this.http
        .get<IDepositAccount[]>(this.resourceUrl, { observe: 'response' })
        // .pipe(
        //   tap((res: EntityArrayResponseType) => this.log.info(`fetched : ${res.body.length} deposit-account items`)),
        // )
        .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)))
    );
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((entity: IDepositAccount) => {
        entity.accountOpeningDate = entity.accountOpeningDate != null ? moment(entity.accountOpeningDate) : moment();
        entity.accountMaturityDate = entity.accountMaturityDate != null ? moment(entity.accountMaturityDate) : moment();
      });
    }
    return res;
  }
}
