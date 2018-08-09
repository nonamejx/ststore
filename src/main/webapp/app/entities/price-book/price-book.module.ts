import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstoreSharedModule } from 'app/shared';
import {
    PriceBookComponent,
    PriceBookDetailComponent,
    PriceBookUpdateComponent,
    PriceBookDeletePopupComponent,
    PriceBookDeleteDialogComponent,
    priceBookRoute,
    priceBookPopupRoute
} from './';

const ENTITY_STATES = [...priceBookRoute, ...priceBookPopupRoute];

@NgModule({
    imports: [StstoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PriceBookComponent,
        PriceBookDetailComponent,
        PriceBookUpdateComponent,
        PriceBookDeleteDialogComponent,
        PriceBookDeletePopupComponent
    ],
    entryComponents: [PriceBookComponent, PriceBookUpdateComponent, PriceBookDeleteDialogComponent, PriceBookDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StstorePriceBookModule {}
