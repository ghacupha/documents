import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DataDisplayRoutingModule } from './data-display-routing.module';
import { BespokeMaterialModule } from 'app/bespoke/bespoke-material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DisplayBranchSummaryModule } from './display-branch-summary/display-branch-summary.module';
import { DisplayCurrencySummaryModule } from './display-currency-summary/display-currency-summary.module';
import { SummaryParamsModule } from './summary-params/summary-params.module';
import { DisplayMoneyMarketSummaryModule } from './display-money-market-summary/display-money-market-summary.module';
import { DisplaySbuSummaryModule } from './display-sbu-summary/display-sbu-summary.module';
import { DisplayTableFSummaryModule } from './display-table-f-summary/display-table-f-summary.module';
import { DisplayTableFNewSummaryModule } from './display-table-f-new-summary/display-table-f-new-summary.module';
import { DisplayDpfSummaryModule } from './display-dpf-summary/display-dpf-summary.module';
import { DocumentsSharedModule } from 'app/shared/shared.module';

@NgModule({
  declarations: [],
  imports: [
    BespokeMaterialModule,
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    DocumentsSharedModule,
    DataDisplayRoutingModule,
    DisplayBranchSummaryModule,
    DisplayCurrencySummaryModule,
    SummaryParamsModule,
    DisplayMoneyMarketSummaryModule,
    DisplaySbuSummaryModule,
    DisplayTableFSummaryModule,
    DisplayTableFNewSummaryModule,
    DisplayDpfSummaryModule
  ],
  exports: [
    DisplayBranchSummaryModule,
    DisplayCurrencySummaryModule,
    DisplayMoneyMarketSummaryModule,
    DisplaySbuSummaryModule,
    DisplayTableFSummaryModule,
    DisplayTableFNewSummaryModule,
    DisplayDpfSummaryModule
  ]
})
export class DataDisplayModule {}
