import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DepositListComponent } from './deposit-list/deposit-list.component';

const routes: Routes = [
  {
    path: 'deposit-list',
    component: DepositListComponent,
    data: {
      pageTitle: 'Data | Deposit List'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DisplayTablesRoutingModule {}
