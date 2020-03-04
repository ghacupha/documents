import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DocumentsTestModule } from '../../../test.module';
import { FormalDocumentUpdateComponent } from 'app/entities/formal-document/formal-document-update.component';
import { FormalDocumentService } from 'app/entities/formal-document/formal-document.service';
import { FormalDocument } from 'app/shared/model/formal-document.model';

describe('Component Tests', () => {
  describe('FormalDocument Management Update Component', () => {
    let comp: FormalDocumentUpdateComponent;
    let fixture: ComponentFixture<FormalDocumentUpdateComponent>;
    let service: FormalDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DocumentsTestModule],
        declarations: [FormalDocumentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FormalDocumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormalDocumentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormalDocumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FormalDocument(123);
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
        const entity = new FormalDocument();
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
