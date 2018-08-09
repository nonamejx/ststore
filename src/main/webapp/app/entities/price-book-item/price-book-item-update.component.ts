import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPriceBookItem } from 'app/shared/model/price-book-item.model';
import { PriceBookItemService } from './price-book-item.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { IPriceBook } from 'app/shared/model/price-book.model';
import { PriceBookService } from 'app/entities/price-book';

@Component({
    selector: 'jhi-price-book-item-update',
    templateUrl: './price-book-item-update.component.html'
})
export class PriceBookItemUpdateComponent implements OnInit {
    private _priceBookItem: IPriceBookItem;
    isSaving: boolean;

    products: IProduct[];

    pricebooks: IPriceBook[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private priceBookItemService: PriceBookItemService,
        private productService: ProductService,
        private priceBookService: PriceBookService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ priceBookItem }) => {
            this.priceBookItem = priceBookItem;
        });
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.priceBookService.query().subscribe(
            (res: HttpResponse<IPriceBook[]>) => {
                this.pricebooks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.priceBookItem.id !== undefined) {
            this.subscribeToSaveResponse(this.priceBookItemService.update(this.priceBookItem));
        } else {
            this.subscribeToSaveResponse(this.priceBookItemService.create(this.priceBookItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPriceBookItem>>) {
        result.subscribe((res: HttpResponse<IPriceBookItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }

    trackPriceBookById(index: number, item: IPriceBook) {
        return item.id;
    }
    get priceBookItem() {
        return this._priceBookItem;
    }

    set priceBookItem(priceBookItem: IPriceBookItem) {
        this._priceBookItem = priceBookItem;
    }
}
