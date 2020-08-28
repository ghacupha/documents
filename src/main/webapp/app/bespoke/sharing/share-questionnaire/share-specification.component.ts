import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import {
  EmailRecipient,
  IEmailRecipient,
  ISharingSpecificationData,
  SharingSpecificationData
} from 'app/bespoke/model/sharing-specification-data.model';
import { ShareSpecificationService } from 'app/bespoke/sharing/share-questionnaire/share-specification.service';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'gha-share-specification',
  templateUrl: './share-specification.component.html',
  styleUrls: ['./share-specification.component.scss']
})
export class ShareSpecificationComponent implements OnInit {
  isSharing = false;
  recipientsArray: IEmailRecipient[] = [];
  recipientForm: FormGroup;

  editForm = this.fb.group({
    sharingTitle: [null, [Validators.required]],
    sharingSubTitle: [null, [Validators.required]],
    briefDescription: [],
    recipientsArray: [null, [Validators.required]],
    documentSharingType: [null, [Validators.required]],
    maximumFileSize: [null, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected shareSpecificationService: ShareSpecificationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private log: NGXLogger
  ) {
    this.recipientForm = this.fb.group({
      recipientsArray: this.fb.array([])
    });
  }

  ngOnInit(): void {
    // Fetch data from activatedRoute or service
    // this.activatedRoute.data.subscribe(({ sharingSpecificationData }) => {
    //   this.updateForm(sharingSpecificationData);
    // });
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
      sharingTitle: specificationData.sharingTitle,
      sharingSubTitle: specificationData.sharingSubTitle,
      briefDescription: specificationData.briefDescription,
      documentSharingType: specificationData.documentSharingType,
      recipientsArray: specificationData.recipients,
      maximumFileSize: specificationData.maximumFileSize
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
    // if (sharingSpecificationData.id !== undefined) {
    // here we are updating a previous pre-existing request
    //   this.subscribeToShareResponse(this.shareSpecificationService.update(sharingSpecificationData));
    // } else {
    //   this.subscribeToShareResponse(this.shareSpecificationService.create(sharingSpecificationData));
    // }
    this.log.debug(`${sharingSpecificationData.toString()}`);
  }

  /**
   * This is used to update share data on the sharing service
   *
   * @private
   */
  private createFromForm(): ISharingSpecificationData {
    return {
      ...new SharingSpecificationData(),
      sharingTitle: this.editForm.get(['sharingTitle'])!.value,
      sharingSubTitle: this.editForm.get(['sharingSubTitle'])!.value,
      briefDescription: this.editForm.get(['briefDescription'])!.value,
      recipients: this.recipientsArray,
      maximumFileSize: this.editForm.get(['maximumFileSize'])!.value,
      documentSharingType: this.editForm.get(['documentSharingType'])!.value
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

  getSelected(selectedVals: ISharingSpecificationData[], option: ISharingSpecificationData): ISharingSpecificationData {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  /**
   * This method returns the form-group elements for each of the recipient
   */
  recipients(): FormArray {
    return this.recipientForm.get('recipientsArray') as FormArray;
  }

  /**
   * This method defines the structure logic of a recipient whose data we add
   * in the recipient array
   */
  newRecipient(): FormGroup {
    return this.fb.group({
      correspondentUsername: '',
      recipientUsername: '',
      recipientEmailAddress: ''
    });
  }

  /**
   * This method adds a recipient details row to the form-group
   */
  addRecipient(): void {
    this.recipients().push(this.newRecipient());
  }

  /**
   * This method removes a row of recipient details from the form-group
   * @param i
   */
  removeRecipient(i: number): void {
    this.recipients().removeAt(i);
  }

  /**
   * This method updates the recipients array with the recipients on the form during the
   * submit method call
   */
  updateRecipients(): IEmailRecipient[] {
    if (this.recipients()) {
      for (let i = 0; i < this.recipients().length; i++) {
        if (this.recipients().at(i) !== null) {
          this.recipientsArray.push({
            ...new EmailRecipient(),
            correspondentUsername: this.recipientForm.get(['correspondentUsername'])!.value,
            recipientUsername: this.recipientForm.get(['recipientUsername'])!.value,
            recipientEmailAddress: this.recipientForm.get(['recipientEmailAddress'])!.value
          });
        }
      }
    }
    return this.recipientsArray;
  }
}
