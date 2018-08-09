import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImageAttribute } from 'app/shared/model/image-attribute.model';
import { ImageAttributeService } from './image-attribute.service';

@Component({
    selector: 'jhi-image-attribute-delete-dialog',
    templateUrl: './image-attribute-delete-dialog.component.html'
})
export class ImageAttributeDeleteDialogComponent {
    imageAttribute: IImageAttribute;

    constructor(
        private imageAttributeService: ImageAttributeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.imageAttributeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'imageAttributeListModification',
                content: 'Deleted an imageAttribute'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-image-attribute-delete-popup',
    template: ''
})
export class ImageAttributeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ imageAttribute }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ImageAttributeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.imageAttribute = imageAttribute;
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
