import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPriceBookItem } from 'app/shared/model/price-book-item.model';
import { PriceBookItemService } from './price-book-item.service';

@Component({
    selector: 'jhi-price-book-item-delete-dialog',
    templateUrl: './price-book-item-delete-dialog.component.html'
})
export class PriceBookItemDeleteDialogComponent {
    priceBookItem: IPriceBookItem;

    constructor(
        private priceBookItemService: PriceBookItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.priceBookItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'priceBookItemListModification',
                content: 'Deleted an priceBookItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-price-book-item-delete-popup',
    template: ''
})
export class PriceBookItemDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ priceBookItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PriceBookItemDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.priceBookItem = priceBookItem;
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
