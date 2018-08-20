import { Component, OnInit } from '@angular/core';
import { IPriceBook } from 'app/shared/model/price-book.model';
import { Account, LoginModalService, Principal } from 'app/core';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PriceSettingService } from 'app/price-setting/price-setting.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IProduct } from 'app/shared/model/product.model';

@Component({
    selector: 'jhi-price-setting',
    templateUrl: './price-setting.component.html',
    styles: []
})
export class PriceSettingComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;

    selectedPriceBook: IPriceBook = null;
    originalProducts: IProduct[];

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private priceSettingService: PriceSettingService,
        private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.priceSettingService.getAllProducts().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.originalProducts = res.body;
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

    onSelectPriceBook(priceBook: IPriceBook): void {
        this.selectedPriceBook = priceBook;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
