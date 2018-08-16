import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ICategory } from 'app/shared/model/category.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { HomeService } from 'app/home/home.service';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-left-menu',
    templateUrl: './left-menu.component.html',
    styleUrls: ['./left-menu.component.css']
})
export class LeftMenuComponent implements OnInit {
    categories: ICategory[];
    selectedCategory: ICategory = null;
    orderedCategories: ICategory[] = [];

    @Output() categoryChange: EventEmitter<ICategory> = new EventEmitter<ICategory>();
    @Output() onClearSearch: EventEmitter<void> = new EventEmitter<void>();

    constructor(private homeService: HomeService, private jhiAlertService: JhiAlertService) {}

    ngOnInit() {
        this.homeService.getAllCategories().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.categories = res.body;
                const rootCategories: ICategory[] = [];
                this.categories.filter(x => x.fatherId === null).forEach(cat => rootCategories.push(cat));
                this.createOrderedCategoryList(rootCategories);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    onSelectCategory(category: ICategory) {
        this.selectedCategory = category;
        // send the selected category to parent
        this.categoryChange.emit(this.selectedCategory);
    }

    clearSearch(): void {
        this.selectedCategory = null;
        // send the selected category to parent
        this.onClearSearch.emit();
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    /**
     * Create an ordered category list from the list of category in the database.
     * Find the root categories (level = 0) and then add the child categories following the father.
     * Ex:
     * 1
     *  3
     *   4
     * 2
     * @param {ICategory[]} rootCategories
     */
    private createOrderedCategoryList(rootCategories: ICategory[]): void {
        rootCategories.forEach(category => {
            this.addToOrderedCategories(category, 0);
        });
    }

    /**
     * A recursive function to add category with a level to the ordered category list.
     *
     * @param {ICategory} rootCategory - the category is to add to the ordered list
     * @param {number} level - the level of a category in the tree
     */
    private addToOrderedCategories(rootCategory: ICategory, level: number): void {
        rootCategory.level = level++;
        this.orderedCategories.push(rootCategory);
        if (this.categories.filter(childCategory => rootCategory.id === childCategory.fatherId).length === 0) {
            return;
        } else {
            this.categories.filter(childCategory => rootCategory.id === childCategory.fatherId).forEach(childCategory => {
                this.addToOrderedCategories(childCategory, level);
            });
        }
    }
}
