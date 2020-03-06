import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BespokeRoutingModule } from './bespoke-routing.module';
import { AboutModule } from 'app/bespoke/about/about.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BespokeMaterialModule } from 'app/bespoke/bespoke-material.module';
import { DataDisplayModule } from './data-display/data-display.module';
import { BespokeNavigationModule } from './bespoke-navigation/bespoke-navigation.module';
import { QuestionnaireModule } from './questionnaire/questionnaire.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AboutModule,
    BespokeMaterialModule,
    ReactiveFormsModule,
    FormsModule,
    BespokeRoutingModule,
    DataDisplayModule,
    BespokeNavigationModule,
    QuestionnaireModule
  ],
  exports: [AboutModule, DataDisplayModule]
})
export class BespokeModule {}