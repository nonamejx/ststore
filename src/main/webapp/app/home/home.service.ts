import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { ICategory } from 'app/shared/model/category.model';
import { createRequestOption } from 'app/shared';
import { IProduct } from 'app/shared/model/product.model';

type EntityArrayResponseType = HttpResponse<ICategory[]>;

@Injectable({
    providedIn: 'root'
})
export class HomeService {
    private categoryResourceUrl = SERVER_API_URL + 'api/public/categories';
    private productResourceUrl = SERVER_API_URL + 'api/public/products';

    constructor(private http: HttpClient) {}

    getAllCategories(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICategory[]>(this.categoryResourceUrl, { params: options, observe: 'response' });
    }

    getAllProducts(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProduct[]>(this.productResourceUrl, { params: options, observe: 'response' });
    }
}
