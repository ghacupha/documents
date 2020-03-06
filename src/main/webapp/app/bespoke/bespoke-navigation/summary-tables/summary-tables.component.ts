import { Component, OnInit } from '@angular/core';
import { LoginService } from 'app/core/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { SummaryParametersService } from 'app/bespoke/data-display/summary-params/summary-parameters.service';
import { RouteStateService } from 'app/bespoke/route-state.service';

/**
 * What am trying to do here is create multiple methods calling the same modal
 * date-query service, but setting different values for summary-table-type.
 * This way after the new component has been brought up, it can query what type of table
 * summary should be brought up.
 * Okay that is one way of doing it. Another is just actually stating out the navigation from the get go and
 * filling it in into the service. Just be sure to reset it when done
 */
@Component({
  selector: 'gha-summary-tables',
  templateUrl: './summary-tables.component.html',
  styleUrls: ['./summary-tables.component.scss']
})
export class SummaryTablesComponent implements OnInit {
  isNavbarCollapsed: boolean;
  modalRef?: NgbModalRef;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private router: Router,
    protected dateParameterService: SummaryParametersService,
    protected stateService: RouteStateService<string>
  ) {
    this.isNavbarCollapsed = false;
  }

  navigateBranchSummary(): void {
    this.stateService.data = 'summary/branch-summary';
    this.dateParameterService.openDateQuery();
  }
  navigateCurrencySummary(): void {
    this.stateService.data = 'summary/currency-summary';
    this.dateParameterService.openDateQuery();
  }
  navigateMoneyMarketReport(): void {
    this.stateService.data = 'summary/money-market-summary';
    this.dateParameterService.openDateQuery();
  }
  navigateSbuReport(): void {
    // TODO Ensure this path exists
    this.stateService.data = 'summary/sbu-summary';
    this.dateParameterService.openDateQuery();
  }
  navigateTableFReport(): void {
    // TODO Ensure this path exists
    this.stateService.data = 'summary/table-f-summary';
    this.dateParameterService.openDateQuery();
  }
  navigateTableFNewReport(): void {
    // TODO Ensure this path exists
    this.stateService.data = 'summary/table-f-new-summary';
    this.dateParameterService.openDateQuery();
  }
  navigateDpfReport(): void {
    // TODO Ensure this path exists
    this.stateService.data = 'summary/dpf-summary';
    this.dateParameterService.openDateQuery();
  }

  ngOnInit(): void {}

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }
}
