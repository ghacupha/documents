import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFormalDocument } from 'app/shared/model/formal-document.model';
import { FormalDocumentService } from './formal-document.service';

@Component({
  templateUrl: './formal-document-delete-dialog.component.html'
})
export class FormalDocumentDeleteDialogComponent {
  formalDocument?: IFormalDocument;

  constructor(
    protected formalDocumentService: FormalDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formalDocumentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('formalDocumentListModification');
      this.activeModal.close();
    });
  }
}
