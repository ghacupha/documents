import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataTablesModule as DTModule } from 'angular-datatables';
import { DisplayTablesRoutingModule } from './display-tables-routing.module';
import { DepositListComponent } from './deposit-list/deposit-list.component';

@NgModule({
  declarations: [DepositListComponent],
  imports: [CommonModule, DTModule, DisplayTablesRoutingModule],
  exports: [DepositListComponent],
  entryComponents: [DepositListComponent]
})
export class DisplayTablesModule {}
