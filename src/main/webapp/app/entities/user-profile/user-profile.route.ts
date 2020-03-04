import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserProfile, UserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';
import { UserProfileComponent } from './user-profile.component';
import { UserProfileDetailComponent } from './user-profile-detail.component';
import { UserProfileUpdateComponent } from './user-profile-update.component';

@Injectable({ providedIn: 'root' })
export class UserProfileResolve implements Resolve<IUserProfile> {
  constructor(private service: UserProfileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserProfile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userProfile: HttpResponse<UserProfile>) => {
          if (userProfile.body) {
            return of(userProfile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserProfile());
  }
}

export const userProfileRoute: Routes = [
  {
    path: '',
    component: UserProfileComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserProfileDetailComponent,
    resolve: {
      userProfile: UserProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserProfileUpdateComponent,
    resolve: {
      userProfile: UserProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserProfileUpdateComponent,
    resolve: {
      userProfile: UserProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserProfiles'
    },
    canActivate: [UserRouteAccessService]
  }
];
