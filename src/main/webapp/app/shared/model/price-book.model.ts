import { Moment } from 'moment';

export interface IPriceBook {
    id?: number;
    priceBookName?: string;
    createdDate?: Moment;
}

export class PriceBook implements IPriceBook {
    constructor(public id?: number, public priceBookName?: string, public createdDate?: Moment) {}
}
