import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TransactionDocMetadataComponent } from 'app/bespoke/data-display/display-tables/transaction-doc-metadata/transaction-doc-metadata.component';

const routes: Routes = [
  {
    path: 'transactions-list',
    component: TransactionDocMetadataComponent,
    data: {
      pageTitle: 'Metadata | Transaction List'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DisplayTablesRoutingModule {}
