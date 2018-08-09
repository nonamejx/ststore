import { IProduct } from 'app/shared/model//product.model';
import { ICategory } from 'app/shared/model//category.model';

export interface ICategory {
    id?: number;
    categoryName?: string;
    products?: IProduct[];
    children?: ICategory[];
    fatherCategoryName?: string;
    fatherId?: number;
}

export class Category implements ICategory {
    constructor(
        public id?: number,
        public categoryName?: string,
        public products?: IProduct[],
        public children?: ICategory[],
        public fatherCategoryName?: string,
        public fatherId?: number
    ) {}
}
