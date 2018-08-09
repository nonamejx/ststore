import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPriceBook } from 'app/shared/model/price-book.model';

@Component({
    selector: 'jhi-price-book-detail',
    templateUrl: './price-book-detail.component.html'
})
export class PriceBookDetailComponent implements OnInit {
    priceBook: IPriceBook;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ priceBook }) => {
            this.priceBook = priceBook;
        });
    }

    previousState() {
        window.history.back();
    }
}
