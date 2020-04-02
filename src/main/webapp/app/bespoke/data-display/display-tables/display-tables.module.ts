import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataTablesModule as DTModule } from 'angular-datatables';
import { DisplayTablesRoutingModule } from './display-tables-routing.module';
import { TransactionDocMetadataComponent } from './transaction-doc-metadata/transaction-doc-metadata.component';

@NgModule({
  declarations: [TransactionDocMetadataComponent],
  imports: [CommonModule, DTModule, DisplayTablesRoutingModule],
  exports: [TransactionDocMetadataComponent],
  entryComponents: [TransactionDocMetadataComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DisplayTablesModule {}
