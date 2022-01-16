package com.example.mealplanner.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.GenericIngredient;
import com.example.mealplanner.model.util.IngredientConfirmItem;
import com.example.mealplanner.wrapper.GenericIngredientListWrapper;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class GenericIngredientViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private CheckBox checkBox;
    final Gson gson = new Gson();
    private List<GenericIngredientListWrapper> checkedIngredients = new ArrayList<>();

    private GenericIngredientListWrapper genericIngredient;
    private IngredientConfirmItem confirmItem;
    private SharedPreferences pref;

    public GenericIngredientViewHolder(@NonNull View itemView) {
        super(itemView);

        pref = itemView.getContext().getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);

        confirmItem = new IngredientConfirmItem();
        textView = itemView.findViewById(R.id.ingredientListItem);
        checkBox = itemView.findViewById(R.id.ingredientListCb);
        checkBox.setVisibility(View.VISIBLE);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                genericIngredient.setSelected(!genericIngredient.isSelected());
                checkBox.setChecked(genericIngredient.isSelected());


                String checkedIngredientsStr = pref.getString("checkedIngredients", null);
                if (genericIngredient.isSelected()) {
                    if (checkedIngredientsStr != null) {
                        GenericIngredientListWrapper[] arr = gson.fromJson(checkedIngredientsStr, GenericIngredientListWrapper[].class);
                        checkedIngredients = new ArrayList<>(Arrays.asList(arr));
                    } else {
                        checkedIngredients = new ArrayList<>();
                    }
                    List<Integer> ids = checkedIngredients.stream().map(i -> i.getGenericIngredient().getId()).collect(Collectors.toList());
                    if(!ids.contains(genericIngredient.getGenericIngredient().getId())) {
                        checkedIngredients.add(genericIngredient);
                    }
                } else {
                    checkedIngredients = new ArrayList<>(Arrays.asList(gson.fromJson(checkedIngredientsStr, GenericIngredientListWrapper[].class)));
                    checkedIngredients.removeIf(i -> i.getGenericIngredient().getId() == genericIngredient.getGenericIngredient().getId());
                }
                pref.edit().putString("checkedIngredients", gson.toJson(checkedIngredients)).apply();

            }
        });

    }


    public void setName(String name) {
        textView.setText(name);
    }


    public GenericIngredientListWrapper getGenericIngredient() {
        return genericIngredient;
    }

    public void setGenericIngredient(GenericIngredientListWrapper genericIngredient) {
        this.genericIngredient = genericIngredient;
        this.textView.setText(genericIngredient.getGenericIngredient().getName());

    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }



    public void setSelected() {
        this.genericIngredient.setSelected(true);
        this.checkBox.setChecked(true);
    }



}
