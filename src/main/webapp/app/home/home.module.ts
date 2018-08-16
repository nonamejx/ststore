import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstoreSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { LeftMenuComponent } from './left-menu/left-menu.component';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductListSearchComponent } from './product-list-search/product-list-search.component';

@NgModule({
    imports: [StstoreSharedModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent, LeftMenuComponent, ProductListComponent, ProductListSearchComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StstoreHomeModule {}
