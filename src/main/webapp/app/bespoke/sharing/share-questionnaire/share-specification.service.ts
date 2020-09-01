import { Injectable } from '@angular/core';
import { DocumentSharingType, ISharingSpecificationData } from 'app/bespoke/model/sharing-specification-data.model';
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

  /**
   * This method will navigate to relevant datatables and ensure the said tables have access
   * to the data they will need
   *
   * @param sharingSpecificationData This is the data that will be used to specify
   * client/recipient information
   */
  share(sharingSpecificationData: ISharingSpecificationData): void {
    this.routeStateService.data = sharingSpecificationData;

    // Navigate to the corresponding component
    if (sharingSpecificationData.documentSharingType === DocumentSharingType.TRANSACTION) {
      this.router.navigate(['/display/transactions-list']);
    } else {
      this.router.navigate(['/display/formal-docs-list']);
    }
  }
}
