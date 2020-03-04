import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFormalDocument, FormalDocument } from 'app/shared/model/formal-document.model';
import { FormalDocumentService } from './formal-document.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'gha-formal-document-update',
  templateUrl: './formal-document-update.component.html'
})
export class FormalDocumentUpdateComponent implements OnInit {
  isSaving = false;
  documentDateDp: any;

  editForm = this.fb.group({
    id: [],
    documentTitle: [null, [Validators.required]],
    documentSubject: [],
    briefDescription: [],
    documentDate: [],
    documentType: [],
    documentStandardNumber: [],
    documentAttachment: [null, [Validators.required]],
    documentAttachmentContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected formalDocumentService: FormalDocumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formalDocument }) => {
      this.updateForm(formalDocument);
    });
  }

  updateForm(formalDocument: IFormalDocument): void {
    this.editForm.patchValue({
      id: formalDocument.id,
      documentTitle: formalDocument.documentTitle,
      documentSubject: formalDocument.documentSubject,
      briefDescription: formalDocument.briefDescription,
      documentDate: formalDocument.documentDate,
      documentType: formalDocument.documentType,
      documentStandardNumber: formalDocument.documentStandardNumber,
      documentAttachment: formalDocument.documentAttachment,
      documentAttachmentContentType: formalDocument.documentAttachmentContentType
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
    const formalDocument = this.createFromForm();
    if (formalDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.formalDocumentService.update(formalDocument));
    } else {
      this.subscribeToSaveResponse(this.formalDocumentService.create(formalDocument));
    }
  }

  private createFromForm(): IFormalDocument {
    return {
      ...new FormalDocument(),
      id: this.editForm.get(['id'])!.value,
      documentTitle: this.editForm.get(['documentTitle'])!.value,
      documentSubject: this.editForm.get(['documentSubject'])!.value,
      briefDescription: this.editForm.get(['briefDescription'])!.value,
      documentDate: this.editForm.get(['documentDate'])!.value,
      documentType: this.editForm.get(['documentType'])!.value,
      documentStandardNumber: this.editForm.get(['documentStandardNumber'])!.value,
      documentAttachmentContentType: this.editForm.get(['documentAttachmentContentType'])!.value,
      documentAttachment: this.editForm.get(['documentAttachment'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormalDocument>>): void {
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
}
