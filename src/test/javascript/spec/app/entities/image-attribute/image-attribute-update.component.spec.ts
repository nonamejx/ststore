/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { StstoreTestModule } from '../../../test.module';
import { ImageAttributeUpdateComponent } from 'app/entities/image-attribute/image-attribute-update.component';
import { ImageAttributeService } from 'app/entities/image-attribute/image-attribute.service';
import { ImageAttribute } from 'app/shared/model/image-attribute.model';

describe('Component Tests', () => {
    describe('ImageAttribute Management Update Component', () => {
        let comp: ImageAttributeUpdateComponent;
        let fixture: ComponentFixture<ImageAttributeUpdateComponent>;
        let service: ImageAttributeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StstoreTestModule],
                declarations: [ImageAttributeUpdateComponent]
            })
                .overrideTemplate(ImageAttributeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ImageAttributeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImageAttributeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ImageAttribute(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.imageAttribute = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ImageAttribute();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.imageAttribute = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
