import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ImageAttribute } from 'app/shared/model/image-attribute.model';
import { ImageAttributeService } from './image-attribute.service';
import { ImageAttributeComponent } from './image-attribute.component';
import { ImageAttributeDetailComponent } from './image-attribute-detail.component';
import { ImageAttributeUpdateComponent } from './image-attribute-update.component';
import { ImageAttributeDeletePopupComponent } from './image-attribute-delete-dialog.component';
import { IImageAttribute } from 'app/shared/model/image-attribute.model';

@Injectable({ providedIn: 'root' })
export class ImageAttributeResolve implements Resolve<IImageAttribute> {
    constructor(private service: ImageAttributeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((imageAttribute: HttpResponse<ImageAttribute>) => imageAttribute.body));
        }
        return of(new ImageAttribute());
    }
}

export const imageAttributeRoute: Routes = [
    {
        path: 'image-attribute',
        component: ImageAttributeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.imageAttribute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'image-attribute/:id/view',
        component: ImageAttributeDetailComponent,
        resolve: {
            imageAttribute: ImageAttributeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.imageAttribute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'image-attribute/new',
        component: ImageAttributeUpdateComponent,
        resolve: {
            imageAttribute: ImageAttributeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.imageAttribute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'image-attribute/:id/edit',
        component: ImageAttributeUpdateComponent,
        resolve: {
            imageAttribute: ImageAttributeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.imageAttribute.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imageAttributePopupRoute: Routes = [
    {
        path: 'image-attribute/:id/delete',
        component: ImageAttributeDeletePopupComponent,
        resolve: {
            imageAttribute: ImageAttributeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ststoreApp.imageAttribute.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
