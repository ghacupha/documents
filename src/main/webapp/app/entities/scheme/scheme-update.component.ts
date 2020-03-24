import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IScheme, Scheme } from 'app/shared/model/scheme.model';
import { SchemeService } from './scheme.service';

@Component({
  selector: 'gha-scheme-update',
  templateUrl: './scheme-update.component.html'
})
export class SchemeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    schemeName: [null, [Validators.required]],
    schemeCode: [null, [Validators.required]],
    schemeDescription: []
  });

  constructor(protected schemeService: SchemeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheme }) => {
      this.updateForm(scheme);
    });
  }

  updateForm(scheme: IScheme): void {
    this.editForm.patchValue({
      id: scheme.id,
      schemeName: scheme.schemeName,
      schemeCode: scheme.schemeCode,
      schemeDescription: scheme.schemeDescription
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scheme = this.createFromForm();
    if (scheme.id !== undefined) {
      this.subscribeToSaveResponse(this.schemeService.update(scheme));
    } else {
      this.subscribeToSaveResponse(this.schemeService.create(scheme));
    }
  }

  private createFromForm(): IScheme {
    return {
      ...new Scheme(),
      id: this.editForm.get(['id'])!.value,
      schemeName: this.editForm.get(['schemeName'])!.value,
      schemeCode: this.editForm.get(['schemeCode'])!.value,
      schemeDescription: this.editForm.get(['schemeDescription'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScheme>>): void {
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
