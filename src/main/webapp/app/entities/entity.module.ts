import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { StstoreProductModule } from './product/product.module';
import { StstoreImageAttributeModule } from './image-attribute/image-attribute.module';
import { StstoreUnitModule } from './unit/unit.module';
import { StstoreCategoryModule } from './category/category.module';
import { StstorePriceBookModule } from './price-book/price-book.module';
import { StstorePriceBookItemModule } from './price-book-item/price-book-item.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        StstoreProductModule,
        StstoreImageAttributeModule,
        StstoreUnitModule,
        StstoreCategoryModule,
        StstorePriceBookModule,
        StstorePriceBookItemModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StstoreEntityModule {}
