export interface IPriceBookItem {
    id?: number;
    newPrice?: number;
    productProductName?: string;
    productId?: number;
    priceBookPriceBookName?: string;
    priceBookId?: number;
}

export class PriceBookItem implements IPriceBookItem {
    constructor(
        public id?: number,
        public newPrice?: number,
        public productProductName?: string,
        public productId?: number,
        public priceBookPriceBookName?: string,
        public priceBookId?: number
    ) {}
}
