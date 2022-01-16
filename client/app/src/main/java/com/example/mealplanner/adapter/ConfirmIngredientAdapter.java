package com.example.mealplanner.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.PantryIngredient;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class ConfirmIngredientAdapter extends RecyclerView.Adapter<ConfirmIngredientViewHolder> {

    private List<PantryIngredient> ingredientList;
    private List<ConfirmIngredientViewHolder> viewHolders;
    private OnConfirmItemClickListener onConfirmItemClickListener;

    public interface OnConfirmItemClickListener {
        void onItemClick(View view,int position);
    }

    public void setOnLongItemClickListener(OnConfirmItemClickListener onConfirmItemClickListener){
        this.onConfirmItemClickListener = onConfirmItemClickListener;
    }

    public ConfirmIngredientAdapter(List<PantryIngredient> list){
        this.ingredientList = list;
        this.viewHolders = new LinkedList<>();
    }



    @NonNull
    @Override
    public ConfirmIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item,parent,false);
        ConfirmIngredientViewHolder viewHolder = new ConfirmIngredientViewHolder(view, onConfirmItemClickListener);
        viewHolders.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmIngredientViewHolder holder, int position) {
        PantryIngredient ingredient = ingredientList.get(position);
        holder.setData(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }


    public List<PantryIngredient> getIngredients(){
        return ingredientList;
    }


}
