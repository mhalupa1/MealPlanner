package com.example.mealplanner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.Pantry;

import java.util.ArrayList;

public class PantryAdapter extends RecyclerView.Adapter<PantryViewHolder>{
    private ArrayList<Pantry> pantryArrayList;
    private OnPantryClickListener onPantryClickListener;
    private OnLongPantryClickListener onLongPantryClickListener;

    public interface OnPantryClickListener {
        void onItemClick(View view,int position);
    }
    public interface OnLongPantryClickListener {
        void onLongItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnPantryClickListener onPantryClickListener){
        this.onPantryClickListener = onPantryClickListener;
    }

    public void setOnLongItemClickListener(OnLongPantryClickListener onLongPantryClickListener){
        this.onLongPantryClickListener = onLongPantryClickListener;
    }

    public PantryAdapter(ArrayList<Pantry> pantryArrayList) {
        this.pantryArrayList = pantryArrayList;
    }

    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_item,parent,false);
        PantryViewHolder pantryViewHolder = new PantryViewHolder(view, onPantryClickListener, onLongPantryClickListener);
        return pantryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        Pantry currPantry = pantryArrayList.get(position);
        holder.getTextView().setText(currPantry.getName());
    }

    @Override
    public int getItemCount() {
        return pantryArrayList.size();
    }

}
