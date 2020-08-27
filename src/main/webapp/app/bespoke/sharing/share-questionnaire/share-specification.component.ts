import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { Observable } from 'rxjs';
import { ISharingSpecificationData, SharingSpecificationData } from 'app/bespoke/model/sharing-specification-data.model';
import { ShareSpecificationService } from 'app/bespoke/sharing/share-questionnaire/share-specification.service';

@Component({
  selector: 'gha-share-specification',
  templateUrl: './share-specification.component.html',
  styleUrls: ['./share-specification.component.scss']
})
export class ShareSpecificationComponent implements OnInit {
  isSharing = false;

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
    protected shareSpecificationService: ShareSpecificationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    // Fetch data from activatedRoute or service
    this.activatedRoute.data.subscribe(({ sharingSpecificationData }) => {
      this.updateForm(sharingSpecificationData);
    });
  }

  /**
   * This will be used to update form if we wanted to revise sharing data.
   * This means the destination components should have access to the service
   * that serves this data and updating that service before navigating back
   *
   * @param specificationData
   */
  updateForm(specificationData: ISharingSpecificationData): void {
    this.editForm.patchValue({
      id: specificationData.id,
      sharingTitle: specificationData.sharingTitle,
      sharingSubTitle: specificationData.sharingSubTitle,
      briefDescription: specificationData.briefDescription,
      documentSharingType: specificationData.documentSharingType,
      recipients: specificationData.recipients,
      maximumFileSize: specificationData.maximumFileSize
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

  /**
   * This method calls the next phase on sharing the documents using the sharing service
   */
  submit(): void {
    this.isSharing = true;
    const sharingSpecificationData = this.createFromForm();
    if (sharingSpecificationData.id !== undefined) {
      // here we are updating a previous pre-existing request
      this.subscribeToShareResponse(this.shareSpecificationService.update(sharingSpecificationData));
    } else {
      this.subscribeToShareResponse(this.shareSpecificationService.create(sharingSpecificationData));
    }
  }

  /**
   * This is used to update share data on the sharing service
   *
   * @private
   */
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

  protected subscribeToShareResponse(result: Observable<ISharingSpecificationData>): void {
    result.subscribe(
      () => this.onShareSuccess(),
      () => this.onShareError()
    );
  }

  protected onShareSuccess(): void {
    this.isSharing = false;
    this.previousState();
  }

  protected onShareError(): void {
    this.isSharing = false;
  }

  trackById(index: number, item: ISharingSpecificationData): any {
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
