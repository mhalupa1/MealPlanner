package com.example.mealplanner.adapter;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.PantryIngredient;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class NestedPantryIngredientAdapter extends RecyclerView.Adapter<NestedPantryIngredientViewHolder> {
    private ArrayList<PantryIngredient> nestedList;
    private OnItemsChangedListener onItemsChangedListener;
    public NestedPantryIngredientAdapter(ArrayList<PantryIngredient> nestedList) {
        this.nestedList = nestedList;
    }

    public interface OnItemsChangedListener{
        void onDateChanged(Editable editable, int p);
        void onAmountChanged(String amount, int p);
        void onLongItemClick(View view, int p);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "dd/MM/yy" , Locale.UK );
        String date = formatter.format(p.getExpirationDate());
        double amnt = p.getIngredient().getGenericIngredient().getMeasuringUnit().getDefaultAmount();
        holder.setDefaultAmount((float)amnt);
        holder.getDatePicker().setText(date);
        holder.getAmntEt().setText(p.getAmount().toString());
        holder.getInitValues();
        holder.getAmountTypeTv().setText(p.getIngredient().getGenericIngredient().getMeasuringUnit().getName());

    }

    @Override
    public int getItemCount() {
        return nestedList.size();
    }
}
