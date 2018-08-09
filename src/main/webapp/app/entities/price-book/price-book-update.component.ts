import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPriceBook } from 'app/shared/model/price-book.model';
import { PriceBookService } from './price-book.service';

@Component({
    selector: 'jhi-price-book-update',
    templateUrl: './price-book-update.component.html'
})
export class PriceBookUpdateComponent implements OnInit {
    private _priceBook: IPriceBook;
    isSaving: boolean;
    createdDateDp: any;

    constructor(private priceBookService: PriceBookService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ priceBook }) => {
            this.priceBook = priceBook;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.priceBook.id !== undefined) {
            this.subscribeToSaveResponse(this.priceBookService.update(this.priceBook));
        } else {
            this.subscribeToSaveResponse(this.priceBookService.create(this.priceBook));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPriceBook>>) {
        result.subscribe((res: HttpResponse<IPriceBook>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get priceBook() {
        return this._priceBook;
    }

    set priceBook(priceBook: IPriceBook) {
        this._priceBook = priceBook;
    }
}
