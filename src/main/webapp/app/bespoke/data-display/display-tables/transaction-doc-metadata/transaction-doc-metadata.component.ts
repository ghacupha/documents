import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { ActivatedRoute, Router } from '@angular/router';
import { TransactionDocMetadataService } from 'app/bespoke/data-display/display-tables/transaction-doc-metadata/transaction-doc-metadata.service';
import { TransactionDocumentDeleteDialogComponent } from 'app/entities/transaction-document/transaction-document-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ITransactionDocumentMetadata } from 'app/bespoke/model/transaction-document-metadata';

@Component({
  selector: 'gha-transaction-doc-metadata',
  templateUrl: './transaction-doc-metadata.component.html',
  styleUrls: ['./transaction-doc-metadata.component.scss']
})
export class TransactionDocMetadataComponent implements OnInit {
  dtOptions!: DataTables.Settings;
  dtTrigger: Subject<any> = new Subject<any>();

  transactionDocMetaDataArray!: ITransactionDocumentMetadata[];

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected jhiAlertService: JhiAlertService,
    private log: NGXLogger,
    private transactionListService: TransactionDocMetadataService,
    protected modalService: NgbModal
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
    this.transactionListService.query().subscribe(
      res => {
        this.transactionDocMetaDataArray = res.body || [];
        // TODO test whether data-tables are created once and only once
        this.dtTrigger.next();
      },
      err => this.onError(err.toString()),
      () => this.log.info(`Extracted ${this.transactionDocMetaDataArray.length}transaction metadata items from API`)
    );
  }

  private firstPassDataUpdate(): void {
    this.transactionListService.query().subscribe(
      res => {
        this.transactionDocMetaDataArray = res.body || [];
        // TODO test whether data-tables are created once and only once
        // this.dtTrigger.next()
      },
      err => this.onError(err.toString()),
      () => this.log.info(`Extracted ${this.transactionDocMetaDataArray.length}transaction metadata items from API`)
    );
  }

  protected onError(errorMessage: string): void {
    this.jhiAlertService.error(errorMessage, null, '');
    this.log.error(`Error while extracting data from API ${errorMessage}`);

    this.previousView();
  }

  private previousView(): void {
    const navigation = this.router.navigate(['transaction-document']);
    navigation.then(() => {
      this.log.debug(`Well! This was not supposed to happen. Review request parameters and reiterate`);
    });
  }

  delete(transactionDocument: ITransactionDocumentMetadata): void {
    const modalRef = this.modalService.open(TransactionDocumentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transactionDocument = transactionDocument;
  }
}
