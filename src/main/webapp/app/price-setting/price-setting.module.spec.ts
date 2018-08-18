import { PriceSettingModule } from './price-setting.module';

describe('PriceSettingModule', () => {
    let priceSettingModule: PriceSettingModule;

    beforeEach(() => {
        priceSettingModule = new PriceSettingModule();
    });

    it('should create an instance', () => {
        expect(priceSettingModule).toBeTruthy();
    });
});
