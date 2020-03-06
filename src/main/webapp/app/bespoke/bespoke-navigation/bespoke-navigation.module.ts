import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BespokeNavigationRoutingModule } from './bespoke-navigation-routing.module';
import { DataTableComponent } from './data-table/data-table.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthExpiredInterceptor } from 'app/blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from 'app/blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from 'app/blocks/interceptor/notification.interceptor';
import { SummaryTablesComponent } from './summary-tables/summary-tables.component';
import { DocumentsSharedModule } from 'app/shared/shared.module';

@NgModule({
  declarations: [DataTableComponent, SummaryTablesComponent],
  imports: [CommonModule, DocumentsSharedModule, BespokeNavigationRoutingModule],
  exports: [DataTableComponent, SummaryTablesComponent],
  entryComponents: [DataTableComponent, SummaryTablesComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true
    }
  ]
})
export class BespokeNavigationModule {}
