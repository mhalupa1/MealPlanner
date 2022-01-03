package com.example.mealplanner.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.GenericIngredient;
import com.example.mealplanner.wrapper.GenericIngredientListWrapper;
import com.google.gson.Gson;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class GenericIngredientViewHolder extends ChildViewHolder {

    private TextView textView;
    private CheckBox checkBox;
    private EditText datepicker;
    private EditText amountEt;
    private TextView amountTypeTv;
    final Calendar calendar = Calendar.getInstance();
    final Gson gson = new Gson();
    private List<GenericIngredient> checkedIngredients = new LinkedList<>();

    private GenericIngredientListWrapper genericIngredient;
    private CategoryAdapter adapter;

    public GenericIngredientViewHolder(View itemView) {
        super(itemView);
        SharedPreferences pref = itemView.getContext().getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);

        textView = itemView.findViewById(R.id.ingredientListItem);
        checkBox = itemView.findViewById(R.id.ingredientListCb);
        datepicker = itemView.findViewById(R.id.ingDatePicker);
        amountEt = itemView.findViewById(R.id.amountEt);
        amountTypeTv = itemView.findViewById(R.id.amountTypeTv);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", genericIngredient.getGenericIngredient().getName());
                genericIngredient.setSelected(!genericIngredient.isSelected());
                checkBox.setChecked(genericIngredient.isSelected());
                datepicker.setVisibility(genericIngredient.isSelected() ? View.VISIBLE : View.GONE);
                amountEt.setVisibility(genericIngredient.isSelected() ? View.VISIBLE : View.GONE);
                amountTypeTv.setVisibility(genericIngredient.isSelected() ? View.VISIBLE : View.GONE);
                String checkedIngredientsStr = pref.getString("checkedIngredients",null);
                if (genericIngredient.isSelected()) {
                    if(checkedIngredientsStr != null){
                        GenericIngredient[] arr = gson.fromJson(checkedIngredientsStr,GenericIngredient[].class);
                        checkedIngredients = new ArrayList<>(Arrays.asList(arr));
                    } else {
                        checkedIngredients = new LinkedList<>();
                    }
                    checkedIngredients.add(genericIngredient.getGenericIngredient());
                } else {
                    checkedIngredients = Arrays.asList(gson.fromJson(checkedIngredientsStr,GenericIngredient[].class));
                    checkedIngredients.remove(genericIngredient.getGenericIngredient());
                }
                pref.edit().putString("checkedIngredients", gson.toJson(checkedIngredients)).apply();

            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
        datepicker.setText(dateFormat.format(calendar.getTime()));
    }

    public void setTexts(){
        amountEt.setText(genericIngredient.getGenericIngredient().getMeasuringUnit().getDefaultAmount().toString());
        amountTypeTv.setText(genericIngredient.getGenericIngredient().getMeasuringUnit().getName());
    }


}
