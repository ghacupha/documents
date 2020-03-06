import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AboutComponent } from 'app/bespoke/about/about.component';
import { RouterModule } from '@angular/router';
import { ABOUT_APP_ROUTE } from 'app/bespoke/about/about-app.route';
import { DocumentsSharedModule } from 'app/shared/shared.module';

@NgModule({
  declarations: [AboutComponent],
  imports: [DocumentsSharedModule, RouterModule.forChild([ABOUT_APP_ROUTE]), CommonModule],
  exports: [AboutComponent],
  entryComponents: [AboutComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AboutModule {}
