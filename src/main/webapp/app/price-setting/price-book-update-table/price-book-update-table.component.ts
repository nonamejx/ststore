import { Component, Input, OnInit } from '@angular/core';
import { IProduct } from 'app/shared/model/product.model';

@Component({
    selector: 'jhi-price-book-update-table',
    templateUrl: './price-book-update-table.component.html',
    styles: []
})
export class PriceBookUpdateTableComponent implements OnInit {
    @Input() products: IProduct[];

    constructor() {}

    ngOnInit() {}
}
