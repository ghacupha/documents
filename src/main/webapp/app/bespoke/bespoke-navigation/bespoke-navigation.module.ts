import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BespokeNavigationRoutingModule } from './bespoke-navigation-routing.module';
import { DataTableComponent } from './data-table/data-table.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthExpiredInterceptor } from 'app/blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from 'app/blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from 'app/blocks/interceptor/notification.interceptor';
import { DocumentsSharedModule } from 'app/shared/shared.module';
import { ShareMenuNavigationComponent } from './share-menu-navigation/share-menu-navigation.component';

@NgModule({
  declarations: [DataTableComponent, ShareMenuNavigationComponent],
  imports: [CommonModule, DocumentsSharedModule, BespokeNavigationRoutingModule],
  exports: [DataTableComponent, ShareMenuNavigationComponent],
  entryComponents: [DataTableComponent, ShareMenuNavigationComponent],
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
