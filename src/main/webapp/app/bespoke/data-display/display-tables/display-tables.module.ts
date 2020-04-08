import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataTablesModule as DTModule } from 'angular-datatables';
import { DisplayTablesRoutingModule } from './display-tables-routing.module';
import { TransactionDocMetadataComponent } from './transaction-doc-metadata/transaction-doc-metadata.component';
import { FormalDocMetadataComponent } from './formal-doc-metadata/formal-doc-metadata.component';
import { DocumentsSharedModule } from 'app/shared/shared.module';

@NgModule({
  declarations: [TransactionDocMetadataComponent, FormalDocMetadataComponent],
  imports: [DocumentsSharedModule, CommonModule, DTModule, DisplayTablesRoutingModule],
  exports: [TransactionDocMetadataComponent, FormalDocMetadataComponent],
  entryComponents: [TransactionDocMetadataComponent, FormalDocMetadataComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DisplayTablesModule {}
