import { Route } from '@angular/router';

import { PriceSettingComponent } from 'app/price-setting/price-setting.component';
import { UserRouteAccessService } from 'app/core';

export const PRICE_SETTING_ROUTE: Route = {
    path: 'price-setting',
    component: PriceSettingComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'priceSetting.title'
    },
    canActivate: [UserRouteAccessService]
};
