package com.example.mealplanner.wrapper;

import com.example.mealplanner.model.PantryIngredient;

import java.util.ArrayList;

public class PantryIngredientWrapper {
    private ArrayList<PantryIngredient> nestedList;
    private String itemText;
    private boolean isExpandable;

    public PantryIngredientWrapper(ArrayList<PantryIngredient> nestedList, String itemText) {
        this.nestedList = nestedList;
        this.itemText = itemText;
        this.isExpandable = true;
    }

    public ArrayList<PantryIngredient> getNestedList() {
        return nestedList;
    }

    public void setNestedList(ArrayList<PantryIngredient> nestedList) {
        this.nestedList = nestedList;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
