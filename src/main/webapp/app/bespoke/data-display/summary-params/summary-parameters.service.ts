import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DateQueryParamComponent } from 'app/bespoke/data-display/summary-params/date-query-param.component';

@Injectable({
  providedIn: 'root'
})
export class SummaryParametersService {
  private isOpen = false;
  constructor(private modalService: NgbModal) {}

  openDateQuery(): NgbModalRef {
    if (this.isOpen) {
      // TODO See how to do an early escape
      throw new Error('The date modal is already open!');
    }
    this.isOpen = true;
    const modalRef = this.modalService.open(DateQueryParamComponent);
    modalRef.result.then(
      () => {
        this.isOpen = false;
      },
      () => {
        this.isOpen = false;
      }
    );
    return modalRef;
  }
}
