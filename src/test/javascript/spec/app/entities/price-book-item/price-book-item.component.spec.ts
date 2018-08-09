/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StstoreTestModule } from '../../../test.module';
import { PriceBookItemComponent } from 'app/entities/price-book-item/price-book-item.component';
import { PriceBookItemService } from 'app/entities/price-book-item/price-book-item.service';
import { PriceBookItem } from 'app/shared/model/price-book-item.model';

describe('Component Tests', () => {
    describe('PriceBookItem Management Component', () => {
        let comp: PriceBookItemComponent;
        let fixture: ComponentFixture<PriceBookItemComponent>;
        let service: PriceBookItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StstoreTestModule],
                declarations: [PriceBookItemComponent],
                providers: []
            })
                .overrideTemplate(PriceBookItemComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PriceBookItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriceBookItemService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PriceBookItem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.priceBookItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
