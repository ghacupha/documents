import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SummaryParamsRoutingModule } from './summary-params-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { BespokeMaterialModule } from 'app/bespoke/bespoke-material.module';
import { FormlyModule } from '@ngx-formly/core';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { DateQueryParamComponent } from './date-query-param.component';
import { QuestionnaireModule } from 'app/bespoke/questionnaire/questionnaire.module';
import { QuestionnaireRoutingModule } from 'app/bespoke/questionnaire/questionnaire-routing.module';

@NgModule({
  declarations: [DateQueryParamComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    BespokeMaterialModule,
    SummaryParamsRoutingModule,
    QuestionnaireModule,
    QuestionnaireRoutingModule,
    FormlyModule.forRoot({
      validationMessages: [{ name: 'required', message: 'This field is required' }]
    }),
    FormlyMaterialModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [DateQueryParamComponent],
  entryComponents: [DateQueryParamComponent]
})
export class SummaryParamsModule {}
