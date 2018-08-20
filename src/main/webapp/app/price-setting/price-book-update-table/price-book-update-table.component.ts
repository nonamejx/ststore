import { Component, Input, OnInit } from '@angular/core';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { Observable } from 'rxjs/Rx';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'jhi-price-book-update-table',
    templateUrl: './price-book-update-table.component.html',
    styles: []
})
export class PriceBookUpdateTableComponent implements OnInit {
    @Input() products: IProduct[];

    constructor(private productService: ProductService, private toast: ToastrService) {}

    ngOnInit() {}

    updateNewPrice(newPrice: number, product: IProduct): void {
        const newPriceNumber: number = Number(newPrice);
        if (product && product.id && newPriceNumber) {
            const clonedProduct = Object.assign({}, product);
            clonedProduct.salePrice = newPriceNumber;
            this.subscribeToSaveResponse(this.productService.update(clonedProduct));
        }
    }

    numberOnly(event, value): boolean {
        const charCode = event.which ? event.which : event.keyCode;
        return !(charCode > 31 && (charCode < 48 || charCode > 57)) && value.length <= 7;
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
        result.subscribe(
            (res: HttpResponse<IProduct>) => this.onSaveSuccess(res.statusText),
            (res: HttpErrorResponse) => this.onSaveError(res.message)
        );
    }

    private onSaveSuccess(message: any) {
        this.toast.success(message, 'Update New Price');
    }

    private onSaveError(message: any) {
        this.toast.error(message, 'Update New Price');
    }
}
