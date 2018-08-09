import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPriceBook } from 'app/shared/model/price-book.model';

type EntityResponseType = HttpResponse<IPriceBook>;
type EntityArrayResponseType = HttpResponse<IPriceBook[]>;

@Injectable({ providedIn: 'root' })
export class PriceBookService {
    private resourceUrl = SERVER_API_URL + 'api/price-books';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/price-books';

    constructor(private http: HttpClient) {}

    create(priceBook: IPriceBook): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(priceBook);
        return this.http
            .post<IPriceBook>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(priceBook: IPriceBook): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(priceBook);
        return this.http
            .put<IPriceBook>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPriceBook>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPriceBook[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPriceBook[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    private convertDateFromClient(priceBook: IPriceBook): IPriceBook {
        const copy: IPriceBook = Object.assign({}, priceBook, {
            createdDate: priceBook.createdDate != null && priceBook.createdDate.isValid() ? priceBook.createdDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((priceBook: IPriceBook) => {
            priceBook.createdDate = priceBook.createdDate != null ? moment(priceBook.createdDate) : null;
        });
        return res;
    }
}
