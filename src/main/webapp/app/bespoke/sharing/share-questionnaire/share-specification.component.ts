import { Component, OnInit } from '@angular/core';
import { IScheme } from 'app/shared/model/scheme.model';
import { FormBuilder, Validators } from '@angular/forms';
import { JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';
import { FormalDocumentService } from 'app/entities/formal-document/formal-document.service';
import { SchemeService } from 'app/entities/scheme/scheme.service';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { FormalDocument, IFormalDocument } from 'app/shared/model/formal-document.model';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { Observable } from 'rxjs';
import { ISharingSpecificationData, SharingSpecificationData } from 'app/bespoke/model/sharing-specification-data.model';

@Component({
  selector: 'gha-share-specification',
  templateUrl: './share-specification.component.html',
  styleUrls: ['./share-specification.component.scss']
})
export class ShareSpecificationComponent implements OnInit {
  isSaving = false;
  schemes: IScheme[] = [];
  documentDateDp: any;

  editForm = this.fb.group({
    id: [],
    sharingTitle: [null, [Validators.required]],
    sharingSubTitle: [null, [Validators.required]],
    briefDescription: [],
    documentType: [null, [Validators.required]],
    recipients: [null, [Validators.required]],
    maximumFileSize: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected formalDocumentService: FormalDocumentService,
    protected schemeService: SchemeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formalDocument }) => {
      this.updateForm(formalDocument);

      this.schemeService.query().subscribe((res: HttpResponse<IScheme[]>) => (this.schemes = res.body || []));
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
      documentAttachmentContentType: formalDocument.documentAttachmentContentType,
      filename: formalDocument.filename,
      schemes: formalDocument.schemes
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

  private createFromForm(): ISharingSpecificationData {
    return {
      ...new SharingSpecificationData(),
      id: this.editForm.get(['id'])!.value,
      sharingTitle: this.editForm.get(['sharingTitle'])!.value,
      sharingSubTitle: this.editForm.get(['sharingSubTitle'])!.value,
      briefDescription: this.editForm.get(['briefDescription'])!.value,
      documentSharingType: this.editForm.get(['documentSharingType'])!.value,
      recipients: this.editForm.get(['recipients'])!.value,
      maximumFileSize: this.editForm.get(['maximumFileSize'])!.value
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
