import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserProfile, UserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { ITransactionDocument } from 'app/shared/model/transaction-document.model';
import { TransactionDocumentService } from 'app/entities/transaction-document/transaction-document.service';

type SelectableEntity = IUser | IDepartment | ITransactionDocument;

@Component({
  selector: 'gha-user-profile-update',
  templateUrl: './user-profile-update.component.html'
})
export class UserProfileUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  departments: IDepartment[] = [];
  transactiondocuments: ITransactionDocument[] = [];

  editForm = this.fb.group({
    id: [],
    staffNumber: [],
    userId: [null, Validators.required],
    departmentId: [],
    transactionDocuments: []
  });

  constructor(
    protected userProfileService: UserProfileService,
    protected userService: UserService,
    protected departmentService: DepartmentService,
    protected transactionDocumentService: TransactionDocumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userProfile }) => {
      this.updateForm(userProfile);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.transactionDocumentService
        .query()
        .subscribe((res: HttpResponse<ITransactionDocument[]>) => (this.transactiondocuments = res.body || []));
    });
  }

  updateForm(userProfile: IUserProfile): void {
    this.editForm.patchValue({
      id: userProfile.id,
      staffNumber: userProfile.staffNumber,
      userId: userProfile.userId,
      departmentId: userProfile.departmentId,
      transactionDocuments: userProfile.transactionDocuments
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userProfile = this.createFromForm();
    if (userProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.userProfileService.update(userProfile));
    } else {
      this.subscribeToSaveResponse(this.userProfileService.create(userProfile));
    }
  }

  private createFromForm(): IUserProfile {
    return {
      ...new UserProfile(),
      id: this.editForm.get(['id'])!.value,
      staffNumber: this.editForm.get(['staffNumber'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      departmentId: this.editForm.get(['departmentId'])!.value,
      transactionDocuments: this.editForm.get(['transactionDocuments'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserProfile>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: ITransactionDocument[], option: ITransactionDocument): ITransactionDocument {
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
