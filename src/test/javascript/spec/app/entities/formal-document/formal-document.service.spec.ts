import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FormalDocumentService } from 'app/entities/formal-document/formal-document.service';
import { IFormalDocument, FormalDocument } from 'app/shared/model/formal-document.model';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';

describe('Service Tests', () => {
  describe('FormalDocument Service', () => {
    let injector: TestBed;
    let service: FormalDocumentService;
    let httpMock: HttpTestingController;
    let elemDefault: IFormalDocument;
    let expectedResult: IFormalDocument | IFormalDocument[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FormalDocumentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new FormalDocument(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        DocumentType.CONTRACT,
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            documentDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FormalDocument', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            documentDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            documentDate: currentDate
          },
          returnedFromService
        );

        service.create(new FormalDocument()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FormalDocument', () => {
        const returnedFromService = Object.assign(
          {
            documentTitle: 'BBBBBB',
            documentSubject: 'BBBBBB',
            briefDescription: 'BBBBBB',
            documentDate: currentDate.format(DATE_FORMAT),
            documentType: 'BBBBBB',
            documentStandardNumber: 'BBBBBB',
            documentAttachment: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            documentDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FormalDocument', () => {
        const returnedFromService = Object.assign(
          {
            documentTitle: 'BBBBBB',
            documentSubject: 'BBBBBB',
            briefDescription: 'BBBBBB',
            documentDate: currentDate.format(DATE_FORMAT),
            documentType: 'BBBBBB',
            documentStandardNumber: 'BBBBBB',
            documentAttachment: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            documentDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FormalDocument', () => {
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
