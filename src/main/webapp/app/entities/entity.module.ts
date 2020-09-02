import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'transaction-document',
        loadChildren: () => import('./transaction-document/transaction-document.module').then(m => m.DocumentsTransactionDocumentModule)
      },
      {
        path: 'formal-document',
        loadChildren: () => import('./formal-document/formal-document.module').then(m => m.DocumentsFormalDocumentModule)
      },
      {
        path: 'scheme',
        loadChildren: () => import('./scheme/scheme.module').then(m => m.DocumentsSchemeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class DocumentsEntityModule {}
