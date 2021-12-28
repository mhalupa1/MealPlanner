package com.example.mealplanner.adapter;

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
import com.example.mealplanner.wrapper.CategoryListWrapper;
import com.example.mealplanner.wrapper.GenericIngredientListWrapper;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class CategoryAdapter extends ExpandableRecyclerViewAdapter<CategoryViewHolder,GenericIngredientViewHolder> {

    LayoutInflater inflater;

    public CategoryAdapter(List<? extends ExpandableGroup> groups, LayoutInflater inflater) {
        super(groups);
        this.inflater = inflater;
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

        if(genericIngredient.isSelected()){
            holder.getCheckBox().setChecked(true);
        } else {
            holder.getCheckBox().setChecked(false);
        }
    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setCategoryTitle(group);
    }
}
