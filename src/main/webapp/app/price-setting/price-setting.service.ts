import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { createRequestOption } from 'app/shared';
import { IProduct } from 'app/shared/model/product.model';
import { ICategory } from 'app/shared/model/category.model';
import { IPriceBook } from 'app/shared/model/price-book.model';

type EntityArrayResponseType = HttpResponse<ICategory[]>;

@Injectable({
    providedIn: 'root'
})
export class PriceSettingService {
    private productResourceUrl = SERVER_API_URL + 'api/products';
    private priceBookResourceUrl = SERVER_API_URL + 'api/price-books';

    constructor(private http: HttpClient) {}

    getAllProducts(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProduct[]>(this.productResourceUrl, { params: options, observe: 'response' });
    }

    getAllPriceBooks(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPriceBook[]>(this.priceBookResourceUrl, { params: options, observe: 'response' });
    }
}
