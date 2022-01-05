package com.example.mealplanner.adapter;

import android.app.DatePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NestedPantryIngredientViewHolder extends RecyclerView.ViewHolder {
    private TextView pantryIngTv;
    private EditText datePicker;
    private ImageView decreaseAmnt;
    private EditText amntEt;
    private ImageView increaseAmnt;
    private TextView amountTypeTv;
    final Calendar calendar = Calendar.getInstance();
    private float currAmnt;
    private float initAmnt;
    public NestedPantryIngredientViewHolder(@NonNull View itemView, NestedPantryIngredientAdapter.OnItemsChangedListener listener) {
        super(itemView);
        pantryIngTv = itemView.findViewById(R.id.pantryIngTv);
        datePicker = itemView.findViewById(R.id.pantryIngDatePicker);
        decreaseAmnt = itemView.findViewById(R.id.decreaseAmountIv);
        amntEt = itemView.findViewById(R.id.pantryIngAmountEt);
        increaseAmnt = itemView.findViewById(R.id.increaseAmountIv);
        amountTypeTv = itemView.findViewById(R.id.pantryIngAmountTypeTv);
        decreaseAmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amntEt.clearFocus();
                float changedAmnt = Float.valueOf(amntEt.getText().toString().trim());
                if(changedAmnt != currAmnt){
                    if(changedAmnt > 0){
                        --changedAmnt;
                        amntEt.setText(String.valueOf(changedAmnt));
                    }
                }else{
                    if(currAmnt > 0){
                        currAmnt -= initAmnt;
                        amntEt.setText(String.valueOf(currAmnt));
                    }
                }


            }
        });
        increaseAmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amntEt.clearFocus();
                float changedAmnt = Float.valueOf(amntEt.getText().toString().trim());
                if(changedAmnt != currAmnt){
                    ++changedAmnt;
                    amntEt.setText(String.valueOf(changedAmnt));
                }else{
                    currAmnt += initAmnt;
                    amntEt.setText(String.valueOf(currAmnt));
                }
            }
        });
        amntEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
        datePicker.setText(dateFormat.format(calendar.getTime()));
    }
    public void getInitAmount(){
        initAmnt = Float.valueOf(amntEt.getText().toString().trim());
        currAmnt = initAmnt;
    }

    public TextView getAmountTypeTv() {
        return amountTypeTv;
    }

    public void setAmountTypeTv(TextView amountTypeTv) {
        this.amountTypeTv = amountTypeTv;
    }

    public TextView getPantryIngTv() {
        return pantryIngTv;
    }

    public void setPantryIngTv(TextView pantryIngTv) {
        this.pantryIngTv = pantryIngTv;
    }

    public EditText getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(EditText datePicker) {
        this.datePicker = datePicker;
    }

    public ImageView getDecreaseAmnt() {
        return decreaseAmnt;
    }

    public void setDecreaseAmnt(ImageView decreaseAmnt) {
        this.decreaseAmnt = decreaseAmnt;
    }

    public EditText getAmntEt() {
        return amntEt;
    }

    public void setAmntEt(EditText amntEt) {
        this.amntEt = amntEt;
    }

    public ImageView getIncreaseAmnt() {
        return increaseAmnt;
    }

    public void setIncreaseAmnt(ImageView increaseAmnt) {
        this.increaseAmnt = increaseAmnt;
    }
}
