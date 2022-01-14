package com.example.mealplanner.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.Category;
import com.example.mealplanner.model.GenericIngredient;
import com.example.mealplanner.model.util.IngredientConfirmItem;
import com.example.mealplanner.wrapper.CategoryListWrapper;
import com.example.mealplanner.wrapper.GenericIngredientListWrapper;
import com.google.gson.Gson;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CategoryAdapter extends ExpandableRecyclerViewAdapter<CategoryViewHolder,GenericIngredientViewHolder> {

    LayoutInflater inflater;

    private List<CategoryViewHolder> parentViewHolders;
    private List<GenericIngredientViewHolder> childViewHolders;


    public CategoryAdapter(List<? extends ExpandableGroup> groups, LayoutInflater inflater, Context context) {
        super(groups);
        this.inflater = inflater;
        childViewHolders = new LinkedList<>();
    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_list_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public GenericIngredientViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ingredient_list_item, parent, false);
        return new GenericIngredientViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(GenericIngredientViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final GenericIngredientListWrapper genericIngredient = ((CategoryListWrapper) group).getItems().get(childIndex);
        holder.setName(genericIngredient.getGenericIngredient().getName());
        holder.setGenericIngredient(genericIngredient);
        holder.setTexts();


        if(genericIngredient.isSelected()){
            holder.getCheckBox().setChecked(true);
        } else {
            holder.getCheckBox().setChecked(false);
        }

        childViewHolders.add(holder);
    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setCategoryTitle(group);
    }

    public List<IngredientConfirmItem> onDestroy(){
        List<IngredientConfirmItem> confirmItems = new LinkedList<>();
        for(GenericIngredientViewHolder holder : childViewHolders){
            if(holder.getGenericIngredient().isSelected()){
                IngredientConfirmItem item = holder.getConfirmItem();
                confirmItems.add(item);
            }
        }
        return confirmItems;
    }

    public void notifyGroupDataChanged() {
        expandableList.expandedGroupIndexes = new boolean[getGroups().size()];
        for (int i = 0; i < getGroups().size(); i++) {
            expandableList.expandedGroupIndexes[i] = false;
        }
    }

}
