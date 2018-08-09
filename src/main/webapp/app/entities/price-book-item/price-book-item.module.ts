import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstoreSharedModule } from 'app/shared';
import {
    PriceBookItemComponent,
    PriceBookItemDetailComponent,
    PriceBookItemUpdateComponent,
    PriceBookItemDeletePopupComponent,
    PriceBookItemDeleteDialogComponent,
    priceBookItemRoute,
    priceBookItemPopupRoute
} from './';

const ENTITY_STATES = [...priceBookItemRoute, ...priceBookItemPopupRoute];

@NgModule({
    imports: [StstoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PriceBookItemComponent,
        PriceBookItemDetailComponent,
        PriceBookItemUpdateComponent,
        PriceBookItemDeleteDialogComponent,
        PriceBookItemDeletePopupComponent
    ],
    entryComponents: [
        PriceBookItemComponent,
        PriceBookItemUpdateComponent,
        PriceBookItemDeleteDialogComponent,
        PriceBookItemDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StstorePriceBookItemModule {}
