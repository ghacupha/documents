import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { DocumentsTestModule } from '../../../test.module';
import { FormalDocumentDetailComponent } from 'app/entities/formal-document/formal-document-detail.component';
import { FormalDocument } from 'app/shared/model/formal-document.model';

describe('Component Tests', () => {
  describe('FormalDocument Management Detail Component', () => {
    let comp: FormalDocumentDetailComponent;
    let fixture: ComponentFixture<FormalDocumentDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ formalDocument: new FormalDocument(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DocumentsTestModule],
        declarations: [FormalDocumentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FormalDocumentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormalDocumentDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load formalDocument on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.formalDocument).toEqual(jasmine.objectContaining({ id: 123 }));
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
