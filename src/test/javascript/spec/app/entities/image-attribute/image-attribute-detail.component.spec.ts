/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StstoreTestModule } from '../../../test.module';
import { ImageAttributeDetailComponent } from 'app/entities/image-attribute/image-attribute-detail.component';
import { ImageAttribute } from 'app/shared/model/image-attribute.model';

describe('Component Tests', () => {
    describe('ImageAttribute Management Detail Component', () => {
        let comp: ImageAttributeDetailComponent;
        let fixture: ComponentFixture<ImageAttributeDetailComponent>;
        const route = ({ data: of({ imageAttribute: new ImageAttribute(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StstoreTestModule],
                declarations: [ImageAttributeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ImageAttributeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ImageAttributeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.imageAttribute).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
