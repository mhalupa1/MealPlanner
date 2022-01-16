package com.example.mealplanner.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;

public class PantryViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    public PantryViewHolder(@NonNull View itemView, PantryAdapter.OnPantryClickListener onPantryClickListener,
                            PantryAdapter.OnLongPantryClickListener onLongPantryClickListener) {
        super(itemView);
        textView = itemView.findViewById(R.id.pantryListItem);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onPantryClickListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        onPantryClickListener.onItemClick(view,position);
                    }
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(onLongPantryClickListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        onLongPantryClickListener.onLongItemClick(view,position);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
