/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { StstoreTestModule } from '../../../test.module';
import { PriceBookUpdateComponent } from 'app/entities/price-book/price-book-update.component';
import { PriceBookService } from 'app/entities/price-book/price-book.service';
import { PriceBook } from 'app/shared/model/price-book.model';

describe('Component Tests', () => {
    describe('PriceBook Management Update Component', () => {
        let comp: PriceBookUpdateComponent;
        let fixture: ComponentFixture<PriceBookUpdateComponent>;
        let service: PriceBookService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StstoreTestModule],
                declarations: [PriceBookUpdateComponent]
            })
                .overrideTemplate(PriceBookUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PriceBookUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriceBookService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PriceBook(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.priceBook = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PriceBook();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.priceBook = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
