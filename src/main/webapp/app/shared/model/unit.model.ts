import { IProduct } from 'app/shared/model//product.model';

export interface IUnit {
    id?: number;
    unitName?: string;
    products?: IProduct[];
}

export class Unit implements IUnit {
    constructor(public id?: number, public unitName?: string, public products?: IProduct[]) {}
}
