import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { DocumentsSharedModule } from 'app/shared/shared.module';
import { DocumentsCoreModule } from 'app/core/core.module';
import { DocumentsAppRoutingModule } from './app-routing.module';
import { DocumentsHomeModule } from './home/home.module';
import { DocumentsEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    DocumentsSharedModule,
    DocumentsCoreModule,
    DocumentsHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    DocumentsEntityModule,
    DocumentsAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class DocumentsAppModule {}
