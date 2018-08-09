/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StstoreTestModule } from '../../../test.module';
import { PriceBookItemDeleteDialogComponent } from 'app/entities/price-book-item/price-book-item-delete-dialog.component';
import { PriceBookItemService } from 'app/entities/price-book-item/price-book-item.service';

describe('Component Tests', () => {
    describe('PriceBookItem Management Delete Component', () => {
        let comp: PriceBookItemDeleteDialogComponent;
        let fixture: ComponentFixture<PriceBookItemDeleteDialogComponent>;
        let service: PriceBookItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StstoreTestModule],
                declarations: [PriceBookItemDeleteDialogComponent]
            })
                .overrideTemplate(PriceBookItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PriceBookItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriceBookItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
