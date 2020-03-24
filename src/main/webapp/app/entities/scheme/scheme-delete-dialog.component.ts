import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScheme } from 'app/shared/model/scheme.model';
import { SchemeService } from './scheme.service';

@Component({
  templateUrl: './scheme-delete-dialog.component.html'
})
export class SchemeDeleteDialogComponent {
  scheme?: IScheme;

  constructor(protected schemeService: SchemeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.schemeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('schemeListModification');
      this.activeModal.close();
    });
  }
}
