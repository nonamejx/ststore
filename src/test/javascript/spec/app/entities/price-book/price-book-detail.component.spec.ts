/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StstoreTestModule } from '../../../test.module';
import { PriceBookDetailComponent } from 'app/entities/price-book/price-book-detail.component';
import { PriceBook } from 'app/shared/model/price-book.model';

describe('Component Tests', () => {
    describe('PriceBook Management Detail Component', () => {
        let comp: PriceBookDetailComponent;
        let fixture: ComponentFixture<PriceBookDetailComponent>;
        const route = ({ data: of({ priceBook: new PriceBook(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StstoreTestModule],
                declarations: [PriceBookDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PriceBookDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PriceBookDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.priceBook).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
