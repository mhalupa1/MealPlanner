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
    private List<IngredientConfirmItem> checkedItems;
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

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public List<IngredientConfirmItem> onDestroy(){
        List<IngredientConfirmItem> confirmItems = new LinkedList<>();
        for(CategoryViewHolder catHolder : parentViewHolders){
            for(GenericIngredientViewHolder ingHolder : catHolder.getAdapter().getViewHolders()) {
                if (ingHolder.getGenericIngredient().isSelected() && ingHolder.getConfirmItem().getIngredient() != null) {
                    IngredientConfirmItem item = ingHolder.getConfirmItem();
                    confirmItems.add(item);
                }
            }
        }
        return confirmItems;
    }

    public void updateItems(List<CategoryListWrapper> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }


    //on search: save data before overriding viewholder list
    public void persistData(){
        List<IngredientConfirmItem> confirmItems = onDestroy();
        List<Integer> confirmItemIds = checkedItems.stream().map(i-> i.getIngredient().getId()).collect(Collectors.toList());

        for(IngredientConfirmItem confirmItem : confirmItems){
            if (confirmItemIds.contains(confirmItem.getIngredient().getId())) {
                int index = checkedItems.indexOf(confirmItem);
                IngredientConfirmItem checkedItem = checkedItems.get(index);
                checkedItem.setAmount(confirmItem.getAmount());
                checkedItem.setDate(confirmItem.getDate());
                checkedItem.setIngredient(checkedItem.getIngredient());
            }
            else {
                checkedItems.add(confirmItem);
                confirmItemIds.add(confirmItem.getIngredient().getId());
            }
        }
    }

    //retrieve date and amount after search
    public void retrieveData(){
        for(IngredientConfirmItem checkedItem: checkedItems) {
            Category category = checkedItem.getIngredient().getCategory();
            Optional<CategoryViewHolder> holderOpt = parentViewHolders.stream()
                    .filter(i -> i.getCategory().getCategory().equals(category)).findFirst();
           if(holderOpt.isPresent()){
                CategoryViewHolder holder = holderOpt.get();
                /**/
                Optional<CategoryViewHolder> tempCatHolderOpt = parentViewHolders.stream()
                        .filter(h -> h.getCategory().equals(holder.getCategory()))
                        .findFirst();

                if(tempCatHolderOpt.isPresent()){
                    CategoryViewHolder tempCatHolder = tempCatHolderOpt.get();
                    List<GenericIngredientViewHolder> childHolders = tempCatHolder.getAdapter().getViewHolders();
                    Optional<GenericIngredientViewHolder> childHolderOpt = childHolders.stream()
                                    .filter(h -> h.getGenericIngredient().equals(checkedItem.getIngredient())).findFirst();
                    if(childHolderOpt.isPresent()){
                        GenericIngredientViewHolder childHolder = childHolderOpt.get();
                        childHolder.setDate(checkedItem.getDate());
                        childHolder.setAmount(checkedItem.getAmount());
                        childHolder.setSelected();
                    }
                }
            }
        }
    }

    public void updateViewHolders(RecyclerView outer){
        int catCount = outer.getChildCount();

        for(int i = 0; i<catCount; ++i){
            CategoryViewHolder outerHolder = (CategoryViewHolder) outer.findViewHolderForAdapterPosition(i);
            parentViewHolders.add(outerHolder);
            RecyclerView inner = outerHolder.getRecyclerView();
            int ingCount = inner.getChildCount();
            NestedGenericIngredientAdapter ingAdapter = outerHolder.getAdapter();
            for(int j = 0; j<ingCount; ++j){
                GenericIngredientViewHolder innerHolder = (GenericIngredientViewHolder) inner.findViewHolderForAdapterPosition(j);
                ingAdapter.addViewHolder(innerHolder);
            }
        }
    }


}
