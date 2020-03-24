import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DocumentsTestModule } from '../../../test.module';
import { SchemeDetailComponent } from 'app/entities/scheme/scheme-detail.component';
import { Scheme } from 'app/shared/model/scheme.model';

describe('Component Tests', () => {
  describe('Scheme Management Detail Component', () => {
    let comp: SchemeDetailComponent;
    let fixture: ComponentFixture<SchemeDetailComponent>;
    const route = ({ data: of({ scheme: new Scheme(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DocumentsTestModule],
        declarations: [SchemeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SchemeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SchemeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load scheme on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.scheme).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
