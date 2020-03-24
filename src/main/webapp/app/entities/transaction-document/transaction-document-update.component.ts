import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITransactionDocument, TransactionDocument } from 'app/shared/model/transaction-document.model';
import { TransactionDocumentService } from './transaction-document.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IScheme } from 'app/shared/model/scheme.model';
import { SchemeService } from 'app/entities/scheme/scheme.service';

@Component({
  selector: 'gha-transaction-document-update',
  templateUrl: './transaction-document-update.component.html'
})
export class TransactionDocumentUpdateComponent implements OnInit {
  isSaving = false;
  schemes: IScheme[] = [];
  transactionDateDp: any;

  editForm = this.fb.group({
    id: [],
    transactionNumber: [null, [Validators.required]],
    transactionDate: [null, [Validators.required]],
    briefDescription: [],
    justification: [],
    transactionAmount: [],
    payeeName: [],
    invoiceNumber: [],
    lpoNumber: [],
    debitNoteNumber: [],
    logisticReferenceNumber: [],
    memoNumber: [],
    documentStandardNumber: [],
    transactionAttachment: [null, [Validators.required]],
    transactionAttachmentContentType: [],
    schemes: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected transactionDocumentService: TransactionDocumentService,
    protected schemeService: SchemeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionDocument }) => {
      this.updateForm(transactionDocument);

      this.schemeService.query().subscribe((res: HttpResponse<IScheme[]>) => (this.schemes = res.body || []));
    });
  }

  updateForm(transactionDocument: ITransactionDocument): void {
    this.editForm.patchValue({
      id: transactionDocument.id,
      transactionNumber: transactionDocument.transactionNumber,
      transactionDate: transactionDocument.transactionDate,
      briefDescription: transactionDocument.briefDescription,
      justification: transactionDocument.justification,
      transactionAmount: transactionDocument.transactionAmount,
      payeeName: transactionDocument.payeeName,
      invoiceNumber: transactionDocument.invoiceNumber,
      lpoNumber: transactionDocument.lpoNumber,
      debitNoteNumber: transactionDocument.debitNoteNumber,
      logisticReferenceNumber: transactionDocument.logisticReferenceNumber,
      memoNumber: transactionDocument.memoNumber,
      documentStandardNumber: transactionDocument.documentStandardNumber,
      transactionAttachment: transactionDocument.transactionAttachment,
      transactionAttachmentContentType: transactionDocument.transactionAttachmentContentType,
      schemes: transactionDocument.schemes
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('documentsApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactionDocument = this.createFromForm();
    if (transactionDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionDocumentService.update(transactionDocument));
    } else {
      this.subscribeToSaveResponse(this.transactionDocumentService.create(transactionDocument));
    }
  }

  private createFromForm(): ITransactionDocument {
    return {
      ...new TransactionDocument(),
      id: this.editForm.get(['id'])!.value,
      transactionNumber: this.editForm.get(['transactionNumber'])!.value,
      transactionDate: this.editForm.get(['transactionDate'])!.value,
      briefDescription: this.editForm.get(['briefDescription'])!.value,
      justification: this.editForm.get(['justification'])!.value,
      transactionAmount: this.editForm.get(['transactionAmount'])!.value,
      payeeName: this.editForm.get(['payeeName'])!.value,
      invoiceNumber: this.editForm.get(['invoiceNumber'])!.value,
      lpoNumber: this.editForm.get(['lpoNumber'])!.value,
      debitNoteNumber: this.editForm.get(['debitNoteNumber'])!.value,
      logisticReferenceNumber: this.editForm.get(['logisticReferenceNumber'])!.value,
      memoNumber: this.editForm.get(['memoNumber'])!.value,
      documentStandardNumber: this.editForm.get(['documentStandardNumber'])!.value,
      transactionAttachmentContentType: this.editForm.get(['transactionAttachmentContentType'])!.value,
      transactionAttachment: this.editForm.get(['transactionAttachment'])!.value,
      schemes: this.editForm.get(['schemes'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionDocument>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IScheme): any {
    return item.id;
  }

  getSelected(selectedVals: IScheme[], option: IScheme): IScheme {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
