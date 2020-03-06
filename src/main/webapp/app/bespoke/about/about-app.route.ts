import { Route } from '@angular/router';
import { AboutComponent } from 'app/bespoke/about/about.component';

export const ABOUT_APP_ROUTE: Route = {
  path: 'documents',
  component: AboutComponent,
  data: {
    authorities: [],
    pageTitle: 'Document Management System'
  }
};
