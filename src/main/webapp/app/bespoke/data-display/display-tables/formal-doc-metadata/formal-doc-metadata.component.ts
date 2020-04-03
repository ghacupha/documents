import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { TransactionDocumentDeleteDialogComponent } from 'app/entities/transaction-document/transaction-document-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';
import { IFormalDocumentMetadata } from 'app/bespoke/model/formal-document-metadata';
import { FormalDocumentMetadataService } from 'app/bespoke/data-display/display-tables/formal-doc-metadata/formal-document-metadata.service';

@Component({
  selector: 'gha-formal-doc-metadata',
  templateUrl: './formal-doc-metadata.component.html',
  styleUrls: ['./formal-doc-metadata.component.scss']
})
export class FormalDocMetadataComponent implements OnInit {
  dtOptions!: DataTables.Settings;
  dtTrigger: Subject<any> = new Subject<any>();

  formalDocMetaDataArray!: IFormalDocumentMetadata[];

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected jhiAlertService: JhiAlertService,
    private log: NGXLogger,
    private listService: FormalDocumentMetadataService,
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
    this.listService.query().subscribe(
      res => {
        this.formalDocMetaDataArray = res.body || [];
        // TODO test whether data-tables are created once and only once
        this.dtTrigger.next();
      },
      err => this.onError(err.toString()),
      () => this.log.info(`Extracted ${this.formalDocMetaDataArray.length}transaction metadata items from API`)
    );
  }

  private firstPassDataUpdate(): void {
    this.listService.query().subscribe(
      res => {
        this.formalDocMetaDataArray = res.body || [];
        // TODO test whether data-tables are created once and only once
        // this.dtTrigger.next()
      },
      err => this.onError(err.toString()),
      () => this.log.info(`Extracted ${this.formalDocMetaDataArray.length}transaction metadata items from API`)
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

  delete(transactionDocument: IFormalDocumentMetadata): void {
    const modalRef = this.modalService.open(TransactionDocumentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transactionDocument = transactionDocument;
  }
}
