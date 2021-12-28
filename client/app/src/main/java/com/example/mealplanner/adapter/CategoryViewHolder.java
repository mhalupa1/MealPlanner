package com.example.mealplanner.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.Category;
import com.example.mealplanner.wrapper.CategoryListWrapper;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class CategoryViewHolder extends GroupViewHolder {

    private TextView textView;
    public CategoryViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.categoryListItem);
    }

    public void setCategoryTitle(ExpandableGroup group) {
        textView.setText(((CategoryListWrapper)group).getCategory().getName());
    }
}
