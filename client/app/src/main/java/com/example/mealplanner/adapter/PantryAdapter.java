package com.example.mealplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.Pantry;

import java.util.ArrayList;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryViewHolder>{
    private ArrayList<Pantry> pantryArrayList;
    private OnItemClickListener onItemClickListener;
    private OnLongItemClickListener onLongItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public interface OnLongItemClickListener{
        void onLongItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener){
        this.onLongItemClickListener = onLongItemClickListener;
    }

    public PantryAdapter(ArrayList<Pantry> pantryArrayList) {
        this.pantryArrayList = pantryArrayList;
    }

    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_item,parent,false);
        PantryViewHolder pantryViewHolder = new PantryViewHolder(view, onItemClickListener, onLongItemClickListener);
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
