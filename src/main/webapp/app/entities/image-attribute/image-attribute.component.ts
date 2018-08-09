import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IImageAttribute } from 'app/shared/model/image-attribute.model';
import { Principal } from 'app/core';
import { ImageAttributeService } from './image-attribute.service';

@Component({
    selector: 'jhi-image-attribute',
    templateUrl: './image-attribute.component.html'
})
export class ImageAttributeComponent implements OnInit, OnDestroy {
    imageAttributes: IImageAttribute[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private imageAttributeService: ImageAttributeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.imageAttributeService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IImageAttribute[]>) => (this.imageAttributes = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.imageAttributeService.query().subscribe(
            (res: HttpResponse<IImageAttribute[]>) => {
                this.imageAttributes = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInImageAttributes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IImageAttribute) {
        return item.id;
    }

    registerChangeInImageAttributes() {
        this.eventSubscriber = this.eventManager.subscribe('imageAttributeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
