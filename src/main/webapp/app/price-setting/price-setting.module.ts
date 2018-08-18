import { NgModule } from '@angular/core';

import { PriceSettingComponent } from './price-setting.component';
import { PriceBookListComponent } from './price-book-list/price-book-list.component';
import { RouterModule } from '@angular/router';
import { PRICE_SETTING_ROUTE } from 'app/price-setting/price-setting.route';
import { StstoreSharedModule } from 'app/shared';
import { PriceBookUpdateTableComponent } from './price-book-update-table/price-book-update-table.component';

@NgModule({
    imports: [StstoreSharedModule, RouterModule.forChild([PRICE_SETTING_ROUTE])],
    declarations: [PriceSettingComponent, PriceBookListComponent, PriceBookUpdateTableComponent]
})
export class PriceSettingModule {}
