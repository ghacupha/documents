import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DocumentsTestModule } from '../../../test.module';
import { SchemeUpdateComponent } from 'app/entities/scheme/scheme-update.component';
import { SchemeService } from 'app/entities/scheme/scheme.service';
import { Scheme } from 'app/shared/model/scheme.model';

describe('Component Tests', () => {
  describe('Scheme Management Update Component', () => {
    let comp: SchemeUpdateComponent;
    let fixture: ComponentFixture<SchemeUpdateComponent>;
    let service: SchemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DocumentsTestModule],
        declarations: [SchemeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SchemeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchemeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchemeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Scheme(123);
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
        const entity = new Scheme();
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
