import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPriceBook } from 'app/shared/model/price-book.model';
import { PriceBookService } from './price-book.service';

@Component({
    selector: 'jhi-price-book-delete-dialog',
    templateUrl: './price-book-delete-dialog.component.html'
})
export class PriceBookDeleteDialogComponent {
    priceBook: IPriceBook;

    constructor(private priceBookService: PriceBookService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.priceBookService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'priceBookListModification',
                content: 'Deleted an priceBook'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-price-book-delete-popup',
    template: ''
})
export class PriceBookDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ priceBook }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PriceBookDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.priceBook = priceBook;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
