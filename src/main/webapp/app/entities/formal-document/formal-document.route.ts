import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFormalDocument, FormalDocument } from 'app/shared/model/formal-document.model';
import { FormalDocumentService } from './formal-document.service';
import { FormalDocumentComponent } from './formal-document.component';
import { FormalDocumentDetailComponent } from './formal-document-detail.component';
import { FormalDocumentUpdateComponent } from './formal-document-update.component';

@Injectable({ providedIn: 'root' })
export class FormalDocumentResolve implements Resolve<IFormalDocument> {
  constructor(private service: FormalDocumentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormalDocument> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((formalDocument: HttpResponse<FormalDocument>) => {
          if (formalDocument.body) {
            return of(formalDocument.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FormalDocument());
  }
}

export const formalDocumentRoute: Routes = [
  {
    path: '',
    component: FormalDocumentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'FormalDocuments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FormalDocumentDetailComponent,
    resolve: {
      formalDocument: FormalDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FormalDocuments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FormalDocumentUpdateComponent,
    resolve: {
      formalDocument: FormalDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FormalDocuments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FormalDocumentUpdateComponent,
    resolve: {
      formalDocument: FormalDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FormalDocuments'
    },
    canActivate: [UserRouteAccessService]
  }
];
