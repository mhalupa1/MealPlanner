package com.example.mealplanner.wrapper;

import com.example.mealplanner.model.PantryIngredient;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PantryIngredientWrapper {
    private ArrayList<PantryIngredient> nestedList;
    private String itemText;
    private boolean isExpandable;
    private BigDecimal totalAmount;
    private String getTotalAmountType;

    public PantryIngredientWrapper(ArrayList<PantryIngredient> nestedList, String itemText, BigDecimal totalAmount, String getTotalAmountType) {
        this.nestedList = nestedList;
        this.itemText = itemText;
        this.totalAmount = totalAmount;
        this.getTotalAmountType = getTotalAmountType;
        isExpandable = true;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getGetTotalAmountType() {
        return getTotalAmountType;
    }

    public void setGetTotalAmountType(String getTotalAmountType) {
        this.getTotalAmountType = getTotalAmountType;
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
