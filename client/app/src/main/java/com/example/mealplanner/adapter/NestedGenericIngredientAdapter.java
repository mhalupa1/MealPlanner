package com.example.mealplanner.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.wrapper.GenericIngredientListWrapper;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

public class NestedGenericIngredientAdapter extends RecyclerView.Adapter<GenericIngredientViewHolder> {

    List<GenericIngredientListWrapper> genericIngredientList;
    List<GenericIngredientViewHolder> viewHolders;

    public NestedGenericIngredientAdapter(List<GenericIngredientListWrapper> ingredients){
        this.genericIngredientList = ingredients;
        viewHolders = new LinkedList<>();
    }

    @NonNull
    @Override
    public GenericIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        GenericIngredientViewHolder holder = new GenericIngredientViewHolder(view);
        viewHolders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenericIngredientViewHolder holder, int position) {
        GenericIngredientListWrapper ingredient = genericIngredientList.get(position);
        holder.setGenericIngredient(ingredient);
        Log.d("ingredient", new Gson().toJson(ingredient));
        holder.setTexts();
    }

    @Override
    public int getItemCount() {
        return genericIngredientList.size();
    }

    public List<GenericIngredientViewHolder> getViewHolders(){
        return viewHolders;
    }

    public void addViewHolder(GenericIngredientViewHolder holder){
        viewHolders.add(holder);
    }

}
