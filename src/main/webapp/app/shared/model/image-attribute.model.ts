export interface IImageAttribute {
    id?: number;
    imageLink?: string;
    productProductName?: string;
    productId?: number;
}

export class ImageAttribute implements IImageAttribute {
    constructor(public id?: number, public imageLink?: string, public productProductName?: string, public productId?: number) {}
}
