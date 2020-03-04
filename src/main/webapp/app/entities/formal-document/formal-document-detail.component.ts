import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFormalDocument } from 'app/shared/model/formal-document.model';

@Component({
  selector: 'gha-formal-document-detail',
  templateUrl: './formal-document-detail.component.html'
})
export class FormalDocumentDetailComponent implements OnInit {
  formalDocument: IFormalDocument | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formalDocument }) => (this.formalDocument = formalDocument));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
