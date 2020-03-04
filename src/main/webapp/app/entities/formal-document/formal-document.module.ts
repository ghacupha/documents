import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DocumentsSharedModule } from 'app/shared/shared.module';
import { FormalDocumentComponent } from './formal-document.component';
import { FormalDocumentDetailComponent } from './formal-document-detail.component';
import { FormalDocumentUpdateComponent } from './formal-document-update.component';
import { FormalDocumentDeleteDialogComponent } from './formal-document-delete-dialog.component';
import { formalDocumentRoute } from './formal-document.route';

@NgModule({
  imports: [DocumentsSharedModule, RouterModule.forChild(formalDocumentRoute)],
  declarations: [
    FormalDocumentComponent,
    FormalDocumentDetailComponent,
    FormalDocumentUpdateComponent,
    FormalDocumentDeleteDialogComponent
  ],
  entryComponents: [FormalDocumentDeleteDialogComponent]
})
export class DocumentsFormalDocumentModule {}
