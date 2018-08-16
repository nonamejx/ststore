import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstoreSharedModule } from 'app/shared';

import {
    ElasticsearchReindexComponent,
    ElasticsearchReindexModalComponent,
    elasticsearchReindexRoute,
    ElasticsearchReindexService
} from './';

const ADMIN_ROUTES = [elasticsearchReindexRoute];

@NgModule({
    imports: [StstoreSharedModule, RouterModule.forChild(ADMIN_ROUTES)],
    declarations: [ElasticsearchReindexComponent, ElasticsearchReindexModalComponent],
    entryComponents: [ElasticsearchReindexModalComponent],
    providers: [ElasticsearchReindexService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StstoreElasticsearchReindexModule {}
