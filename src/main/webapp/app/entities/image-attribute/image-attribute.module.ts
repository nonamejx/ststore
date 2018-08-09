import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstoreSharedModule } from 'app/shared';
import {
    ImageAttributeComponent,
    ImageAttributeDetailComponent,
    ImageAttributeUpdateComponent,
    ImageAttributeDeletePopupComponent,
    ImageAttributeDeleteDialogComponent,
    imageAttributeRoute,
    imageAttributePopupRoute
} from './';

const ENTITY_STATES = [...imageAttributeRoute, ...imageAttributePopupRoute];

@NgModule({
    imports: [StstoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ImageAttributeComponent,
        ImageAttributeDetailComponent,
        ImageAttributeUpdateComponent,
        ImageAttributeDeleteDialogComponent,
        ImageAttributeDeletePopupComponent
    ],
    entryComponents: [
        ImageAttributeComponent,
        ImageAttributeUpdateComponent,
        ImageAttributeDeleteDialogComponent,
        ImageAttributeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StstoreImageAttributeModule {}
