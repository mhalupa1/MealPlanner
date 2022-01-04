package com.example.mealplanner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.PantryIngredient;
import com.example.mealplanner.wrapper.PantryIngredientWrapper;

import java.util.ArrayList;
import java.util.List;

public class PantryIngredientAdapter extends RecyclerView.Adapter<PantryIngredientViewHolder> {
    private List<PantryIngredientWrapper> mList;
    private ArrayList<PantryIngredient> nestedList = new ArrayList<>();

    public PantryIngredientAdapter(List<PantryIngredientWrapper> mList) {
        this.mList = mList;
    }

    @Override
    public PantryIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_category_item, parent, false);
        return  new PantryIngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryIngredientViewHolder holder, int position) {
        PantryIngredientWrapper pantryIngredientWrapper = mList.get(position);
        nestedList = pantryIngredientWrapper.getNestedList();
        holder.getTextView().setText(pantryIngredientWrapper.getItemText());
        boolean isExpandable = pantryIngredientWrapper.isExpandable();
        holder.getExpandableLayout().setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        if(isExpandable){
            holder.getArrowImage().setImageResource(R.drawable.arrow_up);
        }else{
            holder.getArrowImage().setImageResource(R.drawable.arrow_down);
        }
        NestedPantryIngredientAdapter nestedAdapter = new NestedPantryIngredientAdapter(nestedList);
        holder.getNestedRecyclerView().setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.getNestedRecyclerView().setHasFixedSize(true);
        holder.getNestedRecyclerView().setAdapter(nestedAdapter);
        holder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantryIngredientWrapper.setExpandable(!pantryIngredientWrapper.isExpandable());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
