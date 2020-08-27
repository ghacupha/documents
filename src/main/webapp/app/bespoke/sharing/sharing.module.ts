import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShareQuestionnaireModule } from './share-questionnaire/share-questionnaire.module';
import { DocumentsSharedModule } from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';

/**
 * This module contains a number of resources specific to the task of sharing documents
 */
@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild([
      {
        path: 'sharing',
        loadChildren: () => import('./share-questionnaire/share-questionnaire.module').then(m => m.ShareQuestionnaireModule)
      }
    ]),
    CommonModule,
    DocumentsSharedModule,
    ShareQuestionnaireModule
  ],
  exports: [ShareQuestionnaireModule]
})
export class SharingModule {}
