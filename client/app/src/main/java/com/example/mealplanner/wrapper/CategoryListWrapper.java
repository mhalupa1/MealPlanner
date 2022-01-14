package com.example.mealplanner.wrapper;

import com.example.mealplanner.model.Category;
import com.example.mealplanner.model.GenericIngredient;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class CategoryListWrapper extends ExpandableGroup<GenericIngredientListWrapper> {

    private Category category;
    public CategoryListWrapper(String title, List<GenericIngredientListWrapper> items) {
        super(title, items);
    }

    public CategoryListWrapper(Category category, List<GenericIngredientListWrapper> items) {
        super(category.getName(), items);
        this.category = category;
    }



    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
