import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPriceBookItem } from 'app/shared/model/price-book-item.model';

@Component({
    selector: 'jhi-price-book-item-detail',
    templateUrl: './price-book-item-detail.component.html'
})
export class PriceBookItemDetailComponent implements OnInit {
    priceBookItem: IPriceBookItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ priceBookItem }) => {
            this.priceBookItem = priceBookItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
