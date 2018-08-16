import { Component, Input, OnInit } from '@angular/core';
import { IProduct } from 'app/shared/model/product.model';

@Component({
    selector: 'jhi-product-list',
    templateUrl: './product-list.component.html',
    styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
    @Input() products: IProduct[];

    constructor() {}

    ngOnInit() {}
}
