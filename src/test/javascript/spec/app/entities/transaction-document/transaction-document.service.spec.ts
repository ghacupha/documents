import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TransactionDocumentService } from 'app/entities/transaction-document/transaction-document.service';
import { ITransactionDocument, TransactionDocument } from 'app/shared/model/transaction-document.model';

describe('Service Tests', () => {
  describe('TransactionDocument Service', () => {
    let injector: TestBed;
    let service: TransactionDocumentService;
    let httpMock: HttpTestingController;
    let elemDefault: ITransactionDocument;
    let expectedResult: ITransactionDocument | ITransactionDocument[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TransactionDocumentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TransactionDocument(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            transactionDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TransactionDocument', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            transactionDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transactionDate: currentDate
          },
          returnedFromService
        );

        service.create(new TransactionDocument()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TransactionDocument', () => {
        const returnedFromService = Object.assign(
          {
            transactionNumber: 'BBBBBB',
            transactionDate: currentDate.format(DATE_FORMAT),
            briefDescription: 'BBBBBB',
            justification: 'BBBBBB',
            transactionAmount: 1,
            payeeName: 'BBBBBB',
            invoiceNumber: 'BBBBBB',
            lpoNumber: 'BBBBBB',
            debitNoteNumber: 'BBBBBB',
            logisticReferenceNumber: 'BBBBBB',
            memoNumber: 'BBBBBB',
            documentStandardNumber: 'BBBBBB',
            transactionAttachment: 'BBBBBB',
            filename: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transactionDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TransactionDocument', () => {
        const returnedFromService = Object.assign(
          {
            transactionNumber: 'BBBBBB',
            transactionDate: currentDate.format(DATE_FORMAT),
            briefDescription: 'BBBBBB',
            justification: 'BBBBBB',
            transactionAmount: 1,
            payeeName: 'BBBBBB',
            invoiceNumber: 'BBBBBB',
            lpoNumber: 'BBBBBB',
            debitNoteNumber: 'BBBBBB',
            logisticReferenceNumber: 'BBBBBB',
            memoNumber: 'BBBBBB',
            documentStandardNumber: 'BBBBBB',
            transactionAttachment: 'BBBBBB',
            filename: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transactionDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TransactionDocument', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
