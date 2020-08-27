import { Injectable } from '@angular/core';
import { ISharingSpecificationData } from 'app/bespoke/model/sharing-specification-data.model';
import { Observable } from 'rxjs';
import { RouteStateService } from 'app/bespoke/route-state.service';
import { Router } from '@angular/router';

/**
 * In this service we share data across components using both the route-state-service and calling
 * the actual datatable components
 */
@Injectable({
  providedIn: 'root'
})
export class ShareSpecificationService {
  constructor(private routeStateService: RouteStateService<ISharingSpecificationData>, private router: Router) {}

  update(sharingSpecificationData: ISharingSpecificationData): Observable<ISharingSpecificationData> {
    this.routeStateService.data = sharingSpecificationData;

    // Navigate to the corresponding component
    this.router.navigate(['/']);

    return Observable.apply(this.routeStateService);
  }

  create(sharingSpecificationData: ISharingSpecificationData): Observable<ISharingSpecificationData> {
    this.routeStateService.data = sharingSpecificationData;

    // Navigate to the corresponding component
    this.router.navigate(['/']);

    return Observable.apply(this.routeStateService);
  }
}
