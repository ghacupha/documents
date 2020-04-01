import { Component, OnInit } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { JhiAlertService } from 'ng-jhipster';
import { Subject } from 'rxjs/internal/Subject';
import { ActivatedRoute, Router } from '@angular/router';
import { IDepositAccount } from 'app/shared/model/depositAnalysisMain/deposit-account.model';
import { DepositListService } from 'app/bespoke/data-display/display-tables/deposit-list/deposit-list.service';

@Component({
  selector: 'gha-deposit-list',
  templateUrl: './deposit-list.component.html',
  styleUrls: ['./deposit-list.component.scss']
})
export class DepositListComponent implements OnInit {
  dtOptions!: DataTables.Settings;
  dtTrigger: Subject<any> = new Subject<any>();

  deposits!: IDepositAccount[];

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected jhiAlertService: JhiAlertService,
    private log: NGXLogger,
    private depositListService: DepositListService
  ) {
    this.firstPassDataUpdate();
  }

  ngOnInit(): void {
    this.dtOptions = this.getDataTableOptions();
    this.secondPassDataUpdate();
  }

  private getDataTableOptions(): DataTables.Settings {
    return (this.dtOptions = {
      searching: true,
      paging: true,
      pagingType: 'full_numbers',
      pageLength: 5,
      processing: true,
      dom: 'Bfrtip',
      buttons: ['copy', 'csv', 'excel', 'pdf', 'print', 'colvis']
    });
  }

  private secondPassDataUpdate(): void {
    this.depositListService.getDeposits().subscribe(
      res => {
        this.deposits = res.body || [];
        // TODO test whether data-tables are created once and only once
        this.dtTrigger.next();
      },
      err => this.onError(err.toString()),
      () => this.log.info(`Extracted ${this.deposits.length}deposit items from API`)
    );
  }

  private firstPassDataUpdate(): void {
    this.depositListService.getDeposits().subscribe(
      res => {
        this.deposits = res.body || [];
        // TODO test whether data-tables are created once and only once
        // this.dtTrigger.next()
      },
      err => this.onError(err.toString()),
      () => this.log.info(`Extracted ${this.deposits.length} deposit items from API`)
    );
  }

  protected onError(errorMessage: string): void {
    this.jhiAlertService.error(errorMessage, null, '');
    this.log.error(`Error while extracting data from API ${errorMessage}`);

    this.previousView();
  }

  private previousView(): void {
    const navigation = this.router.navigate(['deposit-account']);
    navigation.then(() => {
      this.log.debug(`Well! This was not supposed to happen. Review request parameters and reiterate`);
    });
  }
}
