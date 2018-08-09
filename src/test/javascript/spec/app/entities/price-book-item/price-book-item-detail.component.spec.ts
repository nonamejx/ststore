/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StstoreTestModule } from '../../../test.module';
import { PriceBookItemDetailComponent } from 'app/entities/price-book-item/price-book-item-detail.component';
import { PriceBookItem } from 'app/shared/model/price-book-item.model';

describe('Component Tests', () => {
    describe('PriceBookItem Management Detail Component', () => {
        let comp: PriceBookItemDetailComponent;
        let fixture: ComponentFixture<PriceBookItemDetailComponent>;
        const route = ({ data: of({ priceBookItem: new PriceBookItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StstoreTestModule],
                declarations: [PriceBookItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PriceBookItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PriceBookItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.priceBookItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
