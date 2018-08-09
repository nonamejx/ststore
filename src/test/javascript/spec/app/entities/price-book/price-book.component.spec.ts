/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StstoreTestModule } from '../../../test.module';
import { PriceBookComponent } from 'app/entities/price-book/price-book.component';
import { PriceBookService } from 'app/entities/price-book/price-book.service';
import { PriceBook } from 'app/shared/model/price-book.model';

describe('Component Tests', () => {
    describe('PriceBook Management Component', () => {
        let comp: PriceBookComponent;
        let fixture: ComponentFixture<PriceBookComponent>;
        let service: PriceBookService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StstoreTestModule],
                declarations: [PriceBookComponent],
                providers: []
            })
                .overrideTemplate(PriceBookComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PriceBookComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriceBookService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PriceBook(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.priceBooks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
