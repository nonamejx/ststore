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
            this.filteredProducts = this.filteredProducts.filter(
                p =>
                    p.productName
                        .trim()
                        .toLowerCase()
                        .indexOf(this.searchKey.toLowerCase()) > -1
            );
        }
    }

    private cloneOriginalProducts(): IProduct[] {
        return Object.assign([], this.originalProducts);
    }
}
