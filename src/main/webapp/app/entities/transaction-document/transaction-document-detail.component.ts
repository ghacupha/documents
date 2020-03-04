import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITransactionDocument } from 'app/shared/model/transaction-document.model';

@Component({
  selector: 'gha-transaction-document-detail',
  templateUrl: './transaction-document-detail.component.html'
})
export class TransactionDocumentDetailComponent implements OnInit {
  transactionDocument: ITransactionDocument | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionDocument }) => (this.transactionDocument = transactionDocument));
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
