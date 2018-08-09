import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IImageAttribute } from 'app/shared/model/image-attribute.model';
import { ImageAttributeService } from './image-attribute.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-image-attribute-update',
    templateUrl: './image-attribute-update.component.html'
})
export class ImageAttributeUpdateComponent implements OnInit {
    private _imageAttribute: IImageAttribute;
    isSaving: boolean;

    products: IProduct[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private imageAttributeService: ImageAttributeService,
        private productService: ProductService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ imageAttribute }) => {
            this.imageAttribute = imageAttribute;
        });
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.imageAttribute.id !== undefined) {
            this.subscribeToSaveResponse(this.imageAttributeService.update(this.imageAttribute));
        } else {
            this.subscribeToSaveResponse(this.imageAttributeService.create(this.imageAttribute));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IImageAttribute>>) {
        result.subscribe((res: HttpResponse<IImageAttribute>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
    get imageAttribute() {
        return this._imageAttribute;
    }

    set imageAttribute(imageAttribute: IImageAttribute) {
        this._imageAttribute = imageAttribute;
    }
}
