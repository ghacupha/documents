import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { DocumentsTestModule } from '../../../test.module';
import { TransactionDocumentDetailComponent } from 'app/entities/transaction-document/transaction-document-detail.component';
import { TransactionDocument } from 'app/shared/model/transaction-document.model';

describe('Component Tests', () => {
  describe('TransactionDocument Management Detail Component', () => {
    let comp: TransactionDocumentDetailComponent;
    let fixture: ComponentFixture<TransactionDocumentDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ transactionDocument: new TransactionDocument(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DocumentsTestModule],
        declarations: [TransactionDocumentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionDocumentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionDocumentDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load transactionDocument on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionDocument).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
