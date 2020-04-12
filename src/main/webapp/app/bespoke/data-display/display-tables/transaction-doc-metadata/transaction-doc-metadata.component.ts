import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { ActivatedRoute, Router } from '@angular/router';
import { TransactionDocMetadataService } from 'app/bespoke/data-display/display-tables/transaction-doc-metadata/transaction-doc-metadata.service';
import { TransactionDocumentDeleteDialogComponent } from 'app/entities/transaction-document/transaction-document-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ITransactionDocumentMetadata } from 'app/bespoke/model/transaction-document-metadata';
import {ITransactionDocument} from "app/shared/model/transaction-document.model";
import {Observable} from "rxjs/index";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'gha-transaction-doc-metadata',
  templateUrl: './transaction-doc-metadata.component.html',
  styleUrls: ['./transaction-doc-metadata.component.scss']
})
export class TransactionDocMetadataComponent implements OnInit {
  isSharing = false;
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

  /**
   * Used for marking a document for sharing purposes
   *
   * @param {ITransactionDocumentMetadata} transactionDocument
   */
  onChecked(transactionDocument: ITransactionDocumentMetadata): void {
    transactionDocument.checked = !transactionDocument.checked;
  }

  /**
   * This method is used to triger document-sharing events to the email recipients
   * in the argument, from the component's template
   *
   * @param {string} emailRecipients
   */
  public share(emailRecipients: string): void {
    const sharedDocuments: ITransactionDocumentMetadata[] = this.transactionDocMetaDataArray.filter(x => x.checked);
    const recipients: string[] = emailRecipients.trim().split(';');

    recipients.forEach(recipient => {
      this.subscribeToShareResponse(this.transactionListService.send(recipient, sharedDocuments));
      this.log.debug(`${sharedDocuments.length} documents have been shared, with ${recipient}`);
    });
  }

  private getDataTableOptions(): DataTables.Settings {
    return (this.dtOptions = {
      searching: true,
      paging: true,
      pagingType: 'full_numbers',
      pageLength: 3,
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

  /**
   * Am having problems with triggering the HTTP client. I hope this will trigger the service to call the
   *
   * httpClient and otherwise we will additional features mapped such as the ability to flag when the service is
   *
   * busy sharing, or even notify the user when there was an error
   *
   * @param {Observable<HttpResponse<ITransactionDocumentMetadata[]>>} result
   */
  protected subscribeToShareResponse(result: Observable<HttpResponse<ITransactionDocumentMetadata[]>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError("The service was unable to share the documents, So sorry!")
    );
  }

  protected onSaveSuccess(): void {
    this.isSharing = false;
    this.previousView();
  }

  protected onSaveError(errorMessage: string): void {
    this.isSharing = false;

    this.jhiAlertService.error(errorMessage, null, '');
    this.log.error(`Error while extracting data from API ${errorMessage}`);

    this.previousView();
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
