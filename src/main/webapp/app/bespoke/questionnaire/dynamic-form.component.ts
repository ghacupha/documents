import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { NGXLogger } from 'ngx-logger';
import { QuestionBase } from 'app/bespoke/questionnaire/question-base.model';
import { QuestionControlService } from 'app/bespoke/questionnaire/question-control.service';
import { FormFieldControlService } from 'app/bespoke/questionnaire/form-field-control.service';

@Component({
  selector: 'gha-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.scss']
})
export class DynamicFormComponent implements OnInit, OnDestroy {
  @Input() questions!: QuestionBase<any>[];
  @Output() model = new EventEmitter();
  fields: FormlyFieldConfig[] = [{}];
  queryForm!: FormGroup;
  isSubmitting!: boolean;

  constructor(private qcs: QuestionControlService, private ffcs: FormFieldControlService, private log: NGXLogger) {}

  ngOnInit(): void {
    this.fields = this.ffcs.toFormFieldConfig(this.questions);
    this.queryForm = this.qcs.toFormGroup(this.questions);
    this.log.debug('Dynamic-form initialized...');
  }

  ngOnDestroy(): void {
    this.log.debug('Initializing dynamic form data cleanup OnDestroy callback. Standby...');
    this.questions = [];
    this.fields = [];
  }

  previousState(): void {
    this.log.debug('Request to revert to previous state has been triggered, standby');
    window.history.back();
  }

  submit(): void {
    this.isSubmitting = true;
    this.model.emit(this.queryForm);
  }

  protected onSubmitSuccess(): void {
    this.isSubmitting = false;
    this.previousState();
  }

  protected onSubmitError(): void {
    this.isSubmitting = false;
  }
}
