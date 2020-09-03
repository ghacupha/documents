import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';
import { JhiAlertService } from 'ng-jhipster';
import { NGXLogger } from 'ngx-logger';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';
import { IFormalDocumentMetadata } from 'app/bespoke/model/formal-document-metadata';
import { FormalDocumentMetadataService } from 'app/bespoke/data-display/display-tables/formal-doc-metadata/formal-document-metadata.service';
import { IEmailRecipient, ISharingSpecificationData } from 'app/bespoke/model/sharing-specification-data.model';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { FormalDocumentDeleteDialogComponent } from 'app/entities/formal-document/formal-document-delete-dialog.component';

@Component({
  selector: 'gha-formal-doc-metadata',
  templateUrl: './formal-doc-metadata.component.html',
  styleUrls: ['./formal-doc-metadata.component.scss']
})
export class FormalDocMetadataComponent implements OnInit {
  isSharing = false;
  dtOptions!: DataTables.Settings;
  dtTrigger: Subject<any> = new Subject<any>();

  formalDocMetaDataArray!: IFormalDocumentMetadata[];

  shareSpecificationData!: ISharingSpecificationData;

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
      () => this.log.info(`Extracted ${this.formalDocMetaDataArray.length} formal documents metadata items from API`)
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
      () => this.log.info(`Extracted ${this.formalDocMetaDataArray.length} formal document metadata items from API`)
    );
  }

  protected onError(errorMessage: string): void {
    this.jhiAlertService.error(errorMessage, null, '');
    this.log.error(`Error while extracting data from API ${errorMessage}`);

    this.previousView();
  }

  private previousView(): void {
    const navigation = this.router.navigate(['formal-document']);
    navigation.then(() => {
      this.log.debug(`Well! This was not supposed to happen. Review request parameters and reiterate`);
    });
  }

  delete(formalDocument: IFormalDocumentMetadata): void {
    const modalRef = this.modalService.open(FormalDocumentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.formalDocument = formalDocument;
  }

  /**
   * This method is used to trigger document-sharing events to the email recipients
   * in the argument, from the component's template
   *
   * @param {string} emailRecipients
   * @param {string} recipientUsernames
   */
  public share(): void {
    const sharedDocuments: IFormalDocumentMetadata[] = this.formalDocMetaDataArray.filter(x => x.checked);
    const usernames: string[] = [];
    const recipientEmails: string[] = [];
    const recipientCorrespondents: string[] = [];

    // update username array
    this.shareSpecificationData.recipients!.forEach((recipient: IEmailRecipient) => {
      if (recipient.recipientUsername != null) {
        usernames.push(recipient.recipientUsername);
      }
    });

    // update emails array
    this.shareSpecificationData.recipients!.forEach(recipient => {
      if (recipient.recipientEmailAddress != null) {
        recipientEmails.push(recipient.recipientEmailAddress);
      }
    });

    // update correspondent names array
    this.shareSpecificationData.recipients!.forEach(recipient => {
      if (recipient.recipientEmailAddress != null) {
        if (recipient.correspondentUsername != null) {
          recipientCorrespondents.push(recipient.correspondentUsername);
        }
      }
    });

    const title1: string | undefined = this.shareSpecificationData.sharingTitle;
    const title2: string | undefined = this.shareSpecificationData.sharingSubTitle;
    const brief: string | undefined = this.shareSpecificationData.briefDescription;

    for (let i = 0; i < recipientEmails.length; i++) {
      this.subscribeToShareResponse(
        this.listService.send(usernames[i], recipientEmails[i], title1, title2, sharedDocuments, recipientCorrespondents[i], brief)
      );
      this.log.debug(`${sharedDocuments.length} documents have been shared, with ${recipientEmails[i]} being ${brief}`);
    }
  }

  /**
   * Am having problems with triggering the HTTP client. I hope this will trigger the service to call the
   *
   * httpClient and otherwise we will additional features mapped such as the ability to flag when the service is
   *
   * busy sharing, or even notify the user when there was an error
   *
   * @param {Observable<HttpResponse<IFormalDocumentMetadata[]>>} result
   */
  protected subscribeToShareResponse(result: Observable<HttpResponse<IFormalDocumentMetadata[]>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError('The service was unable to share the documents, So sorry!')
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
}
