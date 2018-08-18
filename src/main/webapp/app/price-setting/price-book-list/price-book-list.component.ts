import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { IPriceBook } from 'app/shared/model/price-book.model';
import { JhiAlertService } from 'ng-jhipster';
import { PriceSettingService } from 'app/price-setting/price-setting.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-price-book-list',
    templateUrl: './price-book-list.component.html',
    styles: []
})
export class PriceBookListComponent implements OnInit {
    priceBooks: IPriceBook[];
    selectedPriceBook: IPriceBook = null;

    @Output() priceBookChange: EventEmitter<IPriceBook> = new EventEmitter<IPriceBook>();

    constructor(private priceSettingService: PriceSettingService, private jhiAlertService: JhiAlertService) {}

    ngOnInit() {
        this.priceSettingService.getAllPriceBooks().subscribe(
            (res: HttpResponse<IPriceBook[]>) => {
                this.priceBooks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    onSelectPriceBook(pricebook: IPriceBook): void {
        this.selectedPriceBook = pricebook;
        this.priceBookChange.emit(this.selectedPriceBook);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
