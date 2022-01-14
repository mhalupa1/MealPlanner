package com.example.mealplanner.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;

public class PantryIngredientViewHolder extends RecyclerView.ViewHolder {
    private LinearLayout linearLayout;
    private RelativeLayout expandableLayout;
    private TextView textView;
    private ImageView arrowImage;
    private RecyclerView nestedRecyclerView;
    private TextView totalAmount;
    private TextView totalAmountType;

    public PantryIngredientViewHolder(@NonNull View itemView) {
        super(itemView);

        linearLayout = itemView.findViewById(R.id.linearLayout);
        expandableLayout = itemView.findViewById(R.id.expandableLayout);
        textView = itemView.findViewById(R.id.itemTv);
        arrowImage = itemView.findViewById(R.id.arrowIv);
        nestedRecyclerView = itemView.findViewById(R.id.childRv);
        totalAmount = itemView.findViewById(R.id.totalAmntTv);
        totalAmountType = itemView.findViewById(R.id.totalAmntTypeTv);
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public RelativeLayout getExpandableLayout() {
        return expandableLayout;
    }

    public void setExpandableLayout(RelativeLayout expandableLayout) {
        this.expandableLayout = expandableLayout;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ImageView getArrowImage() {
        return arrowImage;
    }

    public void setArrowImage(ImageView arrowImage) {
        this.arrowImage = arrowImage;
    }

    public RecyclerView getNestedRecyclerView() {
        return nestedRecyclerView;
    }

    public void setNestedRecyclerView(RecyclerView nestedRecyclerView) {
        this.nestedRecyclerView = nestedRecyclerView;
    }

    public TextView getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(TextView totalAmount) {
        this.totalAmount = totalAmount;
    }

    public TextView getTotalAmountType() {
        return totalAmountType;
    }

    public void setTotalAmountType(TextView totalAmountType) {
        this.totalAmountType = totalAmountType;
    }
}
