import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { PriceBook } from 'app/shared/model/price-book.model';
import { PriceBookService } from './price-book.service';
import { PriceBookComponent } from './price-book.component';
import { PriceBookDetailComponent } from './price-book-detail.component';
import { PriceBookUpdateComponent } from './price-book-update.component';
import { PriceBookDeletePopupComponent } from './price-book-delete-dialog.component';
import { IPriceBook } from 'app/shared/model/price-book.model';

@Injectable({ providedIn: 'root' })
export class PriceBookResolve implements Resolve<IPriceBook> {
    constructor(private service: PriceBookService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((priceBook: HttpResponse<PriceBook>) => priceBook.body);
        }
        return Observable.of(new PriceBook());
    }
}

export const priceBookRoute: Routes = [
    {
        path: 'price-book',
        component: PriceBookComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.priceBook.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'price-book/:id/view',
        component: PriceBookDetailComponent,
        resolve: {
            priceBook: PriceBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.priceBook.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'price-book/new',
        component: PriceBookUpdateComponent,
        resolve: {
            priceBook: PriceBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.priceBook.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'price-book/:id/edit',
        component: PriceBookUpdateComponent,
        resolve: {
            priceBook: PriceBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.priceBook.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const priceBookPopupRoute: Routes = [
    {
        path: 'price-book/:id/delete',
        component: PriceBookDeletePopupComponent,
        resolve: {
            priceBook: PriceBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.priceBook.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
