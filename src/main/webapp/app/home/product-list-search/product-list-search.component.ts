import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
    selector: 'jhi-product-list-search',
    templateUrl: './product-list-search.component.html',
    styles: []
})
export class ProductListSearchComponent implements OnInit {
    currentSearchKey: string;

    @Output() searchKeyEnter: EventEmitter<string> = new EventEmitter<string>();

    constructor() {}

    ngOnInit() {}

    onSearch(): void {
        this.searchKeyEnter.emit(this.currentSearchKey);
    }

    clearSearch(): void {
        this.currentSearchKey = '';
        this.searchKeyEnter.emit(this.currentSearchKey);
    }
}
