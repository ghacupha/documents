import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransactionDocument, TransactionDocument } from 'app/shared/model/transaction-document.model';
import { TransactionDocumentService } from './transaction-document.service';
import { TransactionDocumentComponent } from './transaction-document.component';
import { TransactionDocumentDetailComponent } from './transaction-document-detail.component';
import { TransactionDocumentUpdateComponent } from './transaction-document-update.component';

@Injectable({ providedIn: 'root' })
export class TransactionDocumentResolve implements Resolve<ITransactionDocument> {
  constructor(private service: TransactionDocumentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransactionDocument> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transactionDocument: HttpResponse<TransactionDocument>) => {
          if (transactionDocument.body) {
            return of(transactionDocument.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransactionDocument());
  }
}

export const transactionDocumentRoute: Routes = [
  {
    path: '',
    component: TransactionDocumentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'TransactionDocuments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionDocumentDetailComponent,
    resolve: {
      transactionDocument: TransactionDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionDocuments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionDocumentUpdateComponent,
    resolve: {
      transactionDocument: TransactionDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionDocuments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionDocumentUpdateComponent,
    resolve: {
      transactionDocument: TransactionDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionDocuments'
    },
    canActivate: [UserRouteAccessService]
  }
];
