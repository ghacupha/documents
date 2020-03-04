import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFormalDocument } from 'app/shared/model/formal-document.model';

type EntityResponseType = HttpResponse<IFormalDocument>;
type EntityArrayResponseType = HttpResponse<IFormalDocument[]>;

@Injectable({ providedIn: 'root' })
export class FormalDocumentService {
  public resourceUrl = SERVER_API_URL + 'api/formal-documents';

  constructor(protected http: HttpClient) {}

  create(formalDocument: IFormalDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formalDocument);
    return this.http
      .post<IFormalDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(formalDocument: IFormalDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formalDocument);
    return this.http
      .put<IFormalDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFormalDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFormalDocument[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(formalDocument: IFormalDocument): IFormalDocument {
    const copy: IFormalDocument = Object.assign({}, formalDocument, {
      documentDate:
        formalDocument.documentDate && formalDocument.documentDate.isValid() ? formalDocument.documentDate.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.documentDate = res.body.documentDate ? moment(res.body.documentDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((formalDocument: IFormalDocument) => {
        formalDocument.documentDate = formalDocument.documentDate ? moment(formalDocument.documentDate) : undefined;
      });
    }
    return res;
  }
}
