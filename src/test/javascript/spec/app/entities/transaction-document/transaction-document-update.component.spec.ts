import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DocumentsTestModule } from '../../../test.module';
import { TransactionDocumentUpdateComponent } from 'app/entities/transaction-document/transaction-document-update.component';
import { TransactionDocumentService } from 'app/entities/transaction-document/transaction-document.service';
import { TransactionDocument } from 'app/shared/model/transaction-document.model';

describe('Component Tests', () => {
  describe('TransactionDocument Management Update Component', () => {
    let comp: TransactionDocumentUpdateComponent;
    let fixture: ComponentFixture<TransactionDocumentUpdateComponent>;
    let service: TransactionDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DocumentsTestModule],
        declarations: [TransactionDocumentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionDocumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionDocumentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionDocumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionDocument(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionDocument();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
