import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IScheme } from 'app/shared/model/scheme.model';

type EntityResponseType = HttpResponse<IScheme>;
type EntityArrayResponseType = HttpResponse<IScheme[]>;

@Injectable({ providedIn: 'root' })
export class SchemeService {
  public resourceUrl = SERVER_API_URL + 'api/schemes';

  constructor(protected http: HttpClient) {}

  create(scheme: IScheme): Observable<EntityResponseType> {
    return this.http.post<IScheme>(this.resourceUrl, scheme, { observe: 'response' });
  }

  update(scheme: IScheme): Observable<EntityResponseType> {
    return this.http.put<IScheme>(this.resourceUrl, scheme, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IScheme>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScheme[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
