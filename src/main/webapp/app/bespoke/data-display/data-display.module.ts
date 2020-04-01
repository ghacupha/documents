import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DataDisplayRoutingModule } from './data-display-routing.module';
import { BespokeMaterialModule } from 'app/bespoke/bespoke-material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DocumentsSharedModule } from 'app/shared/shared.module';

@NgModule({
  declarations: [],
  imports: [BespokeMaterialModule, ReactiveFormsModule, FormsModule, CommonModule, DocumentsSharedModule, DataDisplayRoutingModule],
  exports: []
})
export class DataDisplayModule {}
