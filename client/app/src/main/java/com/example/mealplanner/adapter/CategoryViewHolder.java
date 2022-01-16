package com.example.mealplanner.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.wrapper.CategoryListWrapper;
import com.example.mealplanner.wrapper.GenericIngredientListWrapper;

import java.util.List;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private RecyclerView recyclerView;
    private CategoryListWrapper category;
    private List<GenericIngredientListWrapper> items;
    private Context context;
    private View view;
    NestedGenericIngredientAdapter adapter;



    private boolean expanded;

    public CategoryViewHolder(View itemView,RecyclerView recyclerView, Context context,boolean expanded) {
        super(itemView);
        this.view = itemView;
        this.context = context;
        this.expanded = expanded;

        textView = itemView.findViewById(R.id.categoryListItem);
        this.recyclerView = recyclerView;
    }

    public void onCreate() {
        adapter = new NestedGenericIngredientAdapter(items);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(1, 0);
    }


    public void setText(String text) {
        this.textView.setText(text);
    }

    public void setCategoryItems(CategoryListWrapper category) {
        this.category = category;
        this.items = category.getItems();
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public NestedGenericIngredientAdapter getAdapter(){
        return adapter;
    }

    public CategoryListWrapper getCategory(){
        return category;
    }

}
