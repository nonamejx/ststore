import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPriceBookItem } from 'app/shared/model/price-book-item.model';

type EntityResponseType = HttpResponse<IPriceBookItem>;
type EntityArrayResponseType = HttpResponse<IPriceBookItem[]>;

@Injectable({ providedIn: 'root' })
export class PriceBookItemService {
    private resourceUrl = SERVER_API_URL + 'api/price-book-items';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/price-book-items';

    constructor(private http: HttpClient) {}

    create(priceBookItem: IPriceBookItem): Observable<EntityResponseType> {
        return this.http.post<IPriceBookItem>(this.resourceUrl, priceBookItem, { observe: 'response' });
    }

    update(priceBookItem: IPriceBookItem): Observable<EntityResponseType> {
        return this.http.put<IPriceBookItem>(this.resourceUrl, priceBookItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPriceBookItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPriceBookItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPriceBookItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
