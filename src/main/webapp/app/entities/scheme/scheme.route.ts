import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IScheme, Scheme } from 'app/shared/model/scheme.model';
import { SchemeService } from './scheme.service';
import { SchemeComponent } from './scheme.component';
import { SchemeDetailComponent } from './scheme-detail.component';
import { SchemeUpdateComponent } from './scheme-update.component';

@Injectable({ providedIn: 'root' })
export class SchemeResolve implements Resolve<IScheme> {
  constructor(private service: SchemeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScheme> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((scheme: HttpResponse<Scheme>) => {
          if (scheme.body) {
            return of(scheme.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Scheme());
  }
}

export const schemeRoute: Routes = [
  {
    path: '',
    component: SchemeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Schemes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SchemeDetailComponent,
    resolve: {
      scheme: SchemeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Schemes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SchemeUpdateComponent,
    resolve: {
      scheme: SchemeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Schemes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SchemeUpdateComponent,
    resolve: {
      scheme: SchemeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Schemes'
    },
    canActivate: [UserRouteAccessService]
  }
];
