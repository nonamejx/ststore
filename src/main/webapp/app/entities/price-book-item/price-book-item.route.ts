import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PriceBookItem } from 'app/shared/model/price-book-item.model';
import { PriceBookItemService } from './price-book-item.service';
import { PriceBookItemComponent } from './price-book-item.component';
import { PriceBookItemDetailComponent } from './price-book-item-detail.component';
import { PriceBookItemUpdateComponent } from './price-book-item-update.component';
import { PriceBookItemDeletePopupComponent } from './price-book-item-delete-dialog.component';
import { IPriceBookItem } from 'app/shared/model/price-book-item.model';

@Injectable({ providedIn: 'root' })
export class PriceBookItemResolve implements Resolve<IPriceBookItem> {
    constructor(private service: PriceBookItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((priceBookItem: HttpResponse<PriceBookItem>) => priceBookItem.body));
        }
        return of(new PriceBookItem());
    }
}

export const priceBookItemRoute: Routes = [
    {
        path: 'price-book-item',
        component: PriceBookItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.priceBookItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'price-book-item/:id/view',
        component: PriceBookItemDetailComponent,
        resolve: {
            priceBookItem: PriceBookItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.priceBookItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'price-book-item/new',
        component: PriceBookItemUpdateComponent,
        resolve: {
            priceBookItem: PriceBookItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.priceBookItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'price-book-item/:id/edit',
        component: PriceBookItemUpdateComponent,
        resolve: {
            priceBookItem: PriceBookItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.priceBookItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const priceBookItemPopupRoute: Routes = [
    {
        path: 'price-book-item/:id/delete',
        component: PriceBookItemDeletePopupComponent,
        resolve: {
            priceBookItem: PriceBookItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.priceBookItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
