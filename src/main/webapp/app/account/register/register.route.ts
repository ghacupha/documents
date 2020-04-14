import { Route } from '@angular/router';

import { RegisterComponent } from './register.component';
import { Authority } from 'app/shared/constants/authority.constants';

export const registerRoute: Route = {
  path: 'register',
  component: RegisterComponent,
  data: {
    authorities: [Authority.ADMIN],
    pageTitle: 'Registration'
  }
};
