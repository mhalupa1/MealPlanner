package com.example.mealplanner.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Layout;
import android.util.Log;
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
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private List<CategoryViewHolder> parentViewHolders;
    private List<GenericIngredientListWrapper> checkedItems;
    private List<CategoryListWrapper> categories;


    boolean expanded;
    private int mExpandedPosition = -1;


    public CategoryAdapter(List<CategoryListWrapper> categories, Context context) {
        this.categories = categories;
        parentViewHolders = new LinkedList<>();
        checkedItems = new LinkedList<>();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        RecyclerView recyclerView = view.findViewById(R.id.innerConfirmRecycler);
        CategoryViewHolder holder = new CategoryViewHolder(view,recyclerView, parent.getContext(), false);
        parentViewHolders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setText(categories.get(position).getCategory().getName());
        holder.setCategoryItems(categories.get(position));
        holder.onCreate();
        final boolean isExpanded = position==mExpandedPosition;
        holder.getRecyclerView().setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.setExpanded(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public void updateItems(List<CategoryListWrapper> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }




}
