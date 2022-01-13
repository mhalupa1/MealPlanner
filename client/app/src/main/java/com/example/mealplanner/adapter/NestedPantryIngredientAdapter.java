package com.example.mealplanner.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.PantryIngredient;

import java.util.ArrayList;

public class NestedPantryIngredientAdapter extends RecyclerView.Adapter<NestedPantryIngredientViewHolder> {
    ArrayList<PantryIngredient> nestedList;
    OnItemsChangedListener onItemsChangedListener;
    public NestedPantryIngredientAdapter(ArrayList<PantryIngredient> nestedList) {
        this.nestedList = nestedList;
    }

    public interface OnItemsChangedListener{
        void onDateChanged(int position);
        void onAmountChanged(int position);
        void onLongItemClick(View view, int position);
    }


    public void setOnItemsChangedListener(OnItemsChangedListener onItemsChangedListener){
        this.onItemsChangedListener = onItemsChangedListener;
    }
    @Override
    public NestedPantryIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_ingredient_list_item, parent, false);
        return new NestedPantryIngredientViewHolder(view, onItemsChangedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedPantryIngredientViewHolder holder, int position) {
        PantryIngredient p = nestedList.get(position);
        if(p.getIngredient().getName() == null){
            holder.getPantryIngTv().setText(p.getIngredient().getGenericIngredient().getName());
        }else{
            holder.getPantryIngTv().setText(p.getIngredient().getName());
        }
        holder.getDatePicker().setText(p.getExpirationDate().toString());
        holder.getAmntEt().setText(p.getAmount().toString());
        holder.getInitAmount();
        holder.getAmountTypeTv().setText(p.getIngredient().getGenericIngredient().getMeasuringUnit().getName());

    }

    @Override
    public int getItemCount() {
        return nestedList.size();
    }
}
