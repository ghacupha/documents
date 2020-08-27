import { Route } from '@angular/router';
import { ShareSpecificationComponent } from 'app/bespoke/sharing/share-questionnaire/share-specification.component';

export const shareSpecificationRoute: Route = {
  path: 'share-specification',
  component: ShareSpecificationComponent,
  data: {
    authorities: [],
    pageTitle: 'Share Specification'
  }
};
