import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DocumentsSharedModule } from 'app/shared/shared.module';
import { TransactionDocumentComponent } from './transaction-document.component';
import { TransactionDocumentDetailComponent } from './transaction-document-detail.component';
import { TransactionDocumentUpdateComponent } from './transaction-document-update.component';
import { TransactionDocumentDeleteDialogComponent } from './transaction-document-delete-dialog.component';
import { transactionDocumentRoute } from './transaction-document.route';

@NgModule({
  imports: [DocumentsSharedModule, RouterModule.forChild(transactionDocumentRoute)],
  declarations: [
    TransactionDocumentComponent,
    TransactionDocumentDetailComponent,
    TransactionDocumentUpdateComponent,
    TransactionDocumentDeleteDialogComponent
  ],
  entryComponents: [TransactionDocumentDeleteDialogComponent]
})
export class DocumentsTransactionDocumentModule {}
