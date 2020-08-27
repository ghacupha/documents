import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShareSpecificationComponent } from './share-specification.component';
import { Route, RouterModule } from '@angular/router';
import { shareSpecificationRoute } from 'app/bespoke/sharing/share-questionnaire/share-specification.route';
import { DocumentsSharedModule } from 'app/shared/shared.module';

const routes: Route[] = [shareSpecificationRoute];

/**
 * This module contains a component which will be used to fill in details about the sharing
 * data that the user needs to share
 */
@NgModule({
  declarations: [ShareSpecificationComponent],
  imports: [CommonModule, DocumentsSharedModule, RouterModule.forChild(routes)],
  exports: [ShareSpecificationComponent, RouterModule],
  entryComponents: [ShareSpecificationComponent]
})
export class ShareQuestionnaireModule {}
