package com.example.mealplanner.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.GenericIngredient;
import com.example.mealplanner.wrapper.GenericIngredientListWrapper;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class GenericIngredientViewHolder extends ChildViewHolder {

    private TextView textView;
    private CheckBox checkBox;

    private GenericIngredientListWrapper genericIngredient;

    public GenericIngredientViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.ingredientListItem);
        checkBox = itemView.findViewById(R.id.ingredientListCb);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genericIngredient.setSelected(!genericIngredient.isSelected());
                checkBox.setChecked(genericIngredient.isSelected());
            }
        });
    }

    public void setName(String name){
        textView.setText(name);
    }


    public GenericIngredientListWrapper getGenericIngredient() {
        return genericIngredient;
    }

    public void setGenericIngredient(GenericIngredientListWrapper genericIngredient) {
        this.genericIngredient = genericIngredient;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
