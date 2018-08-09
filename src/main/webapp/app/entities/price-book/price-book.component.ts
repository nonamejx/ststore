import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPriceBook } from 'app/shared/model/price-book.model';
import { Principal } from 'app/core';
import { PriceBookService } from './price-book.service';

@Component({
    selector: 'jhi-price-book',
    templateUrl: './price-book.component.html'
})
export class PriceBookComponent implements OnInit, OnDestroy {
    priceBooks: IPriceBook[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private priceBookService: PriceBookService,
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
            this.priceBookService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IPriceBook[]>) => (this.priceBooks = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.priceBookService.query().subscribe(
            (res: HttpResponse<IPriceBook[]>) => {
                this.priceBooks = res.body;
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
        this.registerChangeInPriceBooks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPriceBook) {
        return item.id;
    }

    registerChangeInPriceBooks() {
        this.eventSubscriber = this.eventManager.subscribe('priceBookListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
