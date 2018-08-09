/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StstoreTestModule } from '../../../test.module';
import { ImageAttributeComponent } from 'app/entities/image-attribute/image-attribute.component';
import { ImageAttributeService } from 'app/entities/image-attribute/image-attribute.service';
import { ImageAttribute } from 'app/shared/model/image-attribute.model';

describe('Component Tests', () => {
    describe('ImageAttribute Management Component', () => {
        let comp: ImageAttributeComponent;
        let fixture: ComponentFixture<ImageAttributeComponent>;
        let service: ImageAttributeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StstoreTestModule],
                declarations: [ImageAttributeComponent],
                providers: []
            })
                .overrideTemplate(ImageAttributeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ImageAttributeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImageAttributeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ImageAttribute(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.imageAttributes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
