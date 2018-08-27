import { IImageAttribute } from 'app/shared/model//image-attribute.model';

export interface IProduct {
    id?: number;
    productName?: string;
    productNameSearchable?: string;
    capitalPrice?: number;
    salePrice?: number;
    productDescription?: string;
    imageAttributes?: IImageAttribute[];
    categoryCategoryName?: string;
    categoryId?: number;
    unitUnitName?: string;
    unitId?: number;
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public productName?: string,
        public productNameSearchable?: string,
        public capitalPrice?: number,
        public salePrice?: number,
        public productDescription?: string,
        public imageAttributes?: IImageAttribute[],
        public categoryCategoryName?: string,
        public categoryId?: number,
        public unitUnitName?: string,
        public unitId?: number
    ) {}
}
