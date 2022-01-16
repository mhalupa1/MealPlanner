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
import java.util.List;
import java.util.Locale;

public class GenericIngredientViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private CheckBox checkBox;
    private EditText datepicker;
    private EditText amountEt;
    private TextView amountTypeTv;
    final Calendar calendar = Calendar.getInstance();
    final Gson gson = new Gson();
    private List<IngredientConfirmItem> checkedIngredients = new ArrayList<>();

    private GenericIngredientListWrapper genericIngredient;
    private IngredientConfirmItem confirmItem;
    private SharedPreferences pref;

    public GenericIngredientViewHolder(@NonNull View itemView) {
        super(itemView);

        pref = itemView.getContext().getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);

        confirmItem = new IngredientConfirmItem();
        textView = itemView.findViewById(R.id.ingredientListItem);
        checkBox = itemView.findViewById(R.id.ingredientListCb);
        datepicker = itemView.findViewById(R.id.ingDatePicker);
        amountEt = itemView.findViewById(R.id.amountEt);
        amountTypeTv = itemView.findViewById(R.id.amountTypeTv);
        checkBox.setVisibility(View.VISIBLE);



        checkBox.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                genericIngredient.setSelected(!genericIngredient.isSelected());
                checkBox.setChecked(genericIngredient.isSelected());

                //show views
                datepicker.setVisibility(genericIngredient.isSelected() ? View.VISIBLE : View.GONE);
                amountEt.setVisibility(genericIngredient.isSelected() ? View.VISIBLE : View.GONE);
                amountTypeTv.setVisibility(genericIngredient.isSelected() ? View.VISIBLE : View.GONE);

                String checkedIngredientsStr = pref.getString("checkedIngredients", null);
                if (genericIngredient.isSelected()) {
                    if (checkedIngredientsStr != null) {
                        IngredientConfirmItem[] arr = gson.fromJson(checkedIngredientsStr, IngredientConfirmItem[].class);
                        checkedIngredients = new ArrayList<>(Arrays.asList(arr));
                    } else {
                        checkedIngredients = new ArrayList<>();
                    }
                    confirmItem = new IngredientConfirmItem(genericIngredient.getGenericIngredient(), getCalendarDate(),
                            Double.valueOf(amountEt.getText().toString()));
                    checkedIngredients.add(confirmItem);
                } else {
                    checkedIngredients = new ArrayList<>(Arrays.asList(gson.fromJson(checkedIngredientsStr, IngredientConfirmItem[].class)));
                    checkedIngredients.removeIf(i -> i.getIngredient().getId() == genericIngredient.getGenericIngredient().getId());
                }
                pref.edit().putString("checkedIngredients", gson.toJson(checkedIngredients)).apply();

            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
                logDateChange(year, month + 1, day);
            }
        };

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        amountEt.addTextChangedListener(amountEtListener);
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


    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
        datepicker.setText(dateFormat.format(calendar.getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void logDateChange(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        confirmItem.setDate(date);
    }

    public void setTexts() {
        if(genericIngredient.isSelected()){
            datepicker.setVisibility(View.VISIBLE);
            amountEt.setVisibility(View.VISIBLE);
            amountTypeTv.setVisibility(View.VISIBLE);
            checkBox.setChecked(true);
        }
        Log.d("genericIngredient", new Gson().toJson(genericIngredient));
        textView.setText(genericIngredient.getGenericIngredient().getName());
        amountEt.setText(genericIngredient.getGenericIngredient().getMeasuringUnit().getDefaultAmount().toString());
        amountTypeTv.setText(genericIngredient.getGenericIngredient().getMeasuringUnit().getName());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private LocalDate getCalendarDate() {
        if (calendar.get(Calendar.MONTH) == 0) {
            return null;
        }
        return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    ;

    TextWatcher amountEtListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            confirmItem.setAmount(Double.parseDouble(s.toString()));
        }
    };

    protected IngredientConfirmItem getConfirmItem() {
        return confirmItem;
    }

    public void setDate(LocalDate date) {
        confirmItem.setDate(date);
        if (date != null) {
            Log.d("date", gson.toJson(date));
            calendar.set(Calendar.YEAR, confirmItem.getDate().getYear());
            calendar.set(Calendar.MONTH, confirmItem.getDate().getMonthValue() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, confirmItem.getDate().getDayOfMonth());
            updateLabel();
        }

    }

    public void setAmount(Double amount) {
        this.amountEt.setText(amount.toString());
        this.confirmItem.setAmount(amount);
    }

    public void setSelected() {
        this.genericIngredient.setSelected(true);
        this.checkBox.setChecked(true);
        textView.setText("");
    }



}
