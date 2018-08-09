/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StstoreTestModule } from '../../../test.module';
import { PriceBookDeleteDialogComponent } from 'app/entities/price-book/price-book-delete-dialog.component';
import { PriceBookService } from 'app/entities/price-book/price-book.service';

describe('Component Tests', () => {
    describe('PriceBook Management Delete Component', () => {
        let comp: PriceBookDeleteDialogComponent;
        let fixture: ComponentFixture<PriceBookDeleteDialogComponent>;
        let service: PriceBookService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StstoreTestModule],
                declarations: [PriceBookDeleteDialogComponent]
            })
                .overrideTemplate(PriceBookDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PriceBookDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriceBookService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
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
                )
            );
        });
    });
});
