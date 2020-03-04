import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionDocument } from 'app/shared/model/transaction-document.model';
import { TransactionDocumentService } from './transaction-document.service';

@Component({
  templateUrl: './transaction-document-delete-dialog.component.html'
})
export class TransactionDocumentDeleteDialogComponent {
  transactionDocument?: ITransactionDocument;

  constructor(
    protected transactionDocumentService: TransactionDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transactionDocumentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transactionDocumentListModification');
      this.activeModal.close();
    });
  }
}
