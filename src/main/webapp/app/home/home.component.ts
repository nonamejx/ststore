import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Account, LoginModalService, Principal } from 'app/core';
import { ICategory } from 'app/shared/model/category.model';
import { IProduct } from 'app/shared/model/product.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { HomeService } from 'app/home/home.service';
import { ProductListSearchComponent } from 'app/home/product-list-search/product-list-search.component';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    @ViewChild('searchInput') searchInputRef: ProductListSearchComponent;

    account: Account;
    modalRef: NgbModalRef;

    originalProducts: IProduct[];
    filteredProducts: IProduct[];
    selectedCategory: ICategory;
    searchKey = '';

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private homeService: HomeService,
        private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.homeService.getAllProducts().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.originalProducts = res.body;
                this.filteredProducts = this.cloneOriginalProducts();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    onCategorySelect(category: ICategory): void {
        this.selectedCategory = category;
        this.performFilter();
    }

    onClearSearch(): void {
        this.selectedCategory = null;
        this.searchKey = null;
        this.performFilter();

        this.searchInputRef.clearSearch();
    }

    onSearch(searchKey: string): void {
        this.searchKey = searchKey;
        this.performFilter();
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    private performFilter(): void {
        this.filteredProducts = this.cloneOriginalProducts();
        if (this.selectedCategory) {
            this.filteredProducts = this.originalProducts.filter(p => p.categoryId === this.selectedCategory.id);
        }
        if (this.searchKey && this.searchKey.trim().length > 0) {
            this.searchKey = this.searchKey.toLowerCase();
            this.filteredProducts = this.filteredProducts.filter(p => {
                return (
                    p.id
                        .toString()
                        .trim()
                        .indexOf(this.searchKey) > -1 ||
                    p.productNameSearchable
                        .trim()
                        .toLowerCase()
                        .indexOf(this.searchKey) > -1 ||
                    p.unitUnitName
                        .trim()
                        .toLowerCase()
                        .indexOf(this.searchKey) > -1
                );
            });
        }
    }

    private cloneOriginalProducts(): IProduct[] {
        const cloneArray = Object.assign([], this.originalProducts);
        cloneArray.forEach(p => (p.productNameSearchable = this.convert(p.productName)));
        return cloneArray;
    }

    private convert(originalStr: string): string {
        if (originalStr) {
            let str = originalStr.toLowerCase();
            str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, 'a');
            str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, 'e');
            str = str.replace(/ì|í|ị|ỉ|ĩ/g, 'i');
            str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, 'o');
            str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, 'u');
            str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, 'y');
            str = str.replace(/đ/g, 'd');
            str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\'|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g, ' ');
            str = str.replace(/ + /g, ' ');
            str = str.trim();
            return str;
        }
        return originalStr;
    }
}
