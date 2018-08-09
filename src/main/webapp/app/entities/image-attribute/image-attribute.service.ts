import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IImageAttribute } from 'app/shared/model/image-attribute.model';

type EntityResponseType = HttpResponse<IImageAttribute>;
type EntityArrayResponseType = HttpResponse<IImageAttribute[]>;

@Injectable({ providedIn: 'root' })
export class ImageAttributeService {
    private resourceUrl = SERVER_API_URL + 'api/image-attributes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/image-attributes';

    constructor(private http: HttpClient) {}

    create(imageAttribute: IImageAttribute): Observable<EntityResponseType> {
        return this.http.post<IImageAttribute>(this.resourceUrl, imageAttribute, { observe: 'response' });
    }

    update(imageAttribute: IImageAttribute): Observable<EntityResponseType> {
        return this.http.put<IImageAttribute>(this.resourceUrl, imageAttribute, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IImageAttribute>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IImageAttribute[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IImageAttribute[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
