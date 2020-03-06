import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuestionnaireRoutingModule } from './questionnaire-routing.module';
import { DynamicFormComponent } from './dynamic-form.component';
import { BespokeMaterialModule } from 'app/bespoke/bespoke-material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormlyModule } from '@ngx-formly/core';
import { FormlyMaterialModule } from '@ngx-formly/material';

@NgModule({
  declarations: [DynamicFormComponent],
  imports: [
    CommonModule,
    QuestionnaireRoutingModule,
    BespokeMaterialModule,
    ReactiveFormsModule,
    FormlyModule.forRoot({
      validationMessages: [{ name: 'required', message: 'This field is required' }]
    }),
    FormlyMaterialModule
  ],
  exports: [QuestionnaireRoutingModule, DynamicFormComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  entryComponents: [DynamicFormComponent]
})
export class QuestionnaireModule {}
