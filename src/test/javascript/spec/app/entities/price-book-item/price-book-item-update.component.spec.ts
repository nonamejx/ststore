/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { StstoreTestModule } from '../../../test.module';
import { PriceBookItemUpdateComponent } from 'app/entities/price-book-item/price-book-item-update.component';
import { PriceBookItemService } from 'app/entities/price-book-item/price-book-item.service';
import { PriceBookItem } from 'app/shared/model/price-book-item.model';

describe('Component Tests', () => {
    describe('PriceBookItem Management Update Component', () => {
        let comp: PriceBookItemUpdateComponent;
        let fixture: ComponentFixture<PriceBookItemUpdateComponent>;
        let service: PriceBookItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StstoreTestModule],
                declarations: [PriceBookItemUpdateComponent]
            })
                .overrideTemplate(PriceBookItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PriceBookItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriceBookItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PriceBookItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.priceBookItem = entity;
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
                    const entity = new PriceBookItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.priceBookItem = entity;
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
