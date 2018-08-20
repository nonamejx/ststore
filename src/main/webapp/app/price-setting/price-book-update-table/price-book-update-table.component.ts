import { Component, Input, OnChanges, OnInit } from '@angular/core';
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
export class PriceBookUpdateTableComponent implements OnInit, OnChanges {
    @Input() products: IProduct[];
    filteredProducts: IProduct[];
    currentSearchKey: string;

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

    numberOnly(event): boolean {
        const charCode = event.which ? event.which : event.keyCode;
        return !(charCode > 31 && (charCode < 48 || charCode > 57));
    }

    ngOnChanges() {
        if (this.products) {
            this.filteredProducts = this.cloneProducts(this.products);
        }
    }

    onSearch(): void {
        this.filteredProducts = this.cloneProducts(this.products);
        if (this.currentSearchKey && this.currentSearchKey.length > 0) {
            const searchKey = this.currentSearchKey.trim().toLowerCase();
            this.filteredProducts = this.filteredProducts.filter(p => {
                return (
                    p.id
                        .toString()
                        .trim()
                        .indexOf(searchKey) > -1 ||
                    p.productName
                        .trim()
                        .toLowerCase()
                        .indexOf(searchKey) > -1 ||
                    p.unitUnitName
                        .trim()
                        .toLowerCase()
                        .indexOf(searchKey) > -1
                );
            });
        }
    }

    onClearSearch(): void {
        this.currentSearchKey = '';
        this.filteredProducts = this.cloneProducts(this.products);
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

    private cloneProducts(products: IProduct[]): IProduct[] {
        return Object.assign([], products);
    }
}
