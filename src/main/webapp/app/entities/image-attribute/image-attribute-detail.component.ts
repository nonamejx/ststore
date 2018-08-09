import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImageAttribute } from 'app/shared/model/image-attribute.model';

@Component({
    selector: 'jhi-image-attribute-detail',
    templateUrl: './image-attribute-detail.component.html'
})
export class ImageAttributeDetailComponent implements OnInit {
    imageAttribute: IImageAttribute;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ imageAttribute }) => {
            this.imageAttribute = imageAttribute;
        });
    }

    previousState() {
        window.history.back();
    }
}
