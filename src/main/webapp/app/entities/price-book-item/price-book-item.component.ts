import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPriceBookItem } from 'app/shared/model/price-book-item.model';
import { Principal } from 'app/core';
import { PriceBookItemService } from './price-book-item.service';

@Component({
    selector: 'jhi-price-book-item',
    templateUrl: './price-book-item.component.html'
})
export class PriceBookItemComponent implements OnInit, OnDestroy {
    priceBookItems: IPriceBookItem[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private priceBookItemService: PriceBookItemService,
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
            this.priceBookItemService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IPriceBookItem[]>) => (this.priceBookItems = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.priceBookItemService.query().subscribe(
            (res: HttpResponse<IPriceBookItem[]>) => {
                this.priceBookItems = res.body;
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
        this.registerChangeInPriceBookItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPriceBookItem) {
        return item.id;
    }

    registerChangeInPriceBookItems() {
        this.eventSubscriber = this.eventManager.subscribe('priceBookItemListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
