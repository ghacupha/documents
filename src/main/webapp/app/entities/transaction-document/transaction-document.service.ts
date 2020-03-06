import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransactionDocument } from 'app/shared/model/transaction-document.model';

type EntityResponseType = HttpResponse<ITransactionDocument>;
type EntityArrayResponseType = HttpResponse<ITransactionDocument[]>;

@Injectable({ providedIn: 'root' })
export class TransactionDocumentService {
  public resourceUrl = SERVER_API_URL + 'api/app/transaction-documents';

  constructor(protected http: HttpClient) {}

  create(transactionDocument: ITransactionDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionDocument);
    return this.http
      .post<ITransactionDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transactionDocument: ITransactionDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionDocument);
    return this.http
      .put<ITransactionDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransactionDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionDocument[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(transactionDocument: ITransactionDocument): ITransactionDocument {
    const copy: ITransactionDocument = Object.assign({}, transactionDocument, {
      transactionDate:
        transactionDocument.transactionDate && transactionDocument.transactionDate.isValid()
          ? transactionDocument.transactionDate.format(DATE_FORMAT)
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transactionDate = res.body.transactionDate ? moment(res.body.transactionDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transactionDocument: ITransactionDocument) => {
        transactionDocument.transactionDate = transactionDocument.transactionDate ? moment(transactionDocument.transactionDate) : undefined;
      });
    }
    return res;
  }
}
