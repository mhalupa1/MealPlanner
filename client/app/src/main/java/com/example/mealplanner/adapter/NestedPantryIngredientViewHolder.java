package com.example.mealplanner.adapter;

import android.app.DatePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private float initAmnt;
    private boolean amountChanged = false;
    private boolean dateInitialized = false;
    private String latestDate;
    private float defaultAmount;
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
                amntEt.setFocusable(false);
                float changedAmnt = Float.valueOf(amntEt.getText().toString().trim());
                float newAmount = changedAmnt - defaultAmount;
                if(!(newAmount >= 0))
                    newAmount = 0.0f;
                amntEt.setText(String.valueOf(newAmount));
                listener.onAmountChanged(String.valueOf(newAmount), getAdapterPosition());

            }
        });
        increaseAmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amntEt.setFocusable(false);
                float changedAmnt = Float.valueOf(amntEt.getText().toString().trim());
                changedAmnt += defaultAmount;
                amntEt.setText(String.valueOf(changedAmnt));
                listener.onAmountChanged(String.valueOf(changedAmnt), getAdapterPosition());
            }
        });
        amntEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amntEt.setFocusable(true);
                amntEt.requestFocus();
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
        datePicker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!dateInitialized){
                    dateInitialized = true;
                }else{
                    if(!latestDate.equals(editable.toString())){
                        latestDate = editable.toString();
                        listener.onDateChanged(editable,getAdapterPosition());
                    }

                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onLongItemClick(view,position);
                        return true;
                    }
                }
                return false;
            }
        });
        amntEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Float amount;
                try{
                    amount = Float.valueOf(editable.toString());
                    if(initAmnt != amount){
                        amountChanged = true;
                    }
                }catch(Exception e){

                }

            }
        });
        amntEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && amountChanged) {
                    String amount = amntEt.getText().toString().trim();
                    listener.onAmountChanged(amount, getAdapterPosition());
                    amountChanged = false;
                }

            }
        });

    }
    public void setDefaultAmount(float defaultAmount){
        this.defaultAmount = defaultAmount;
    }
   private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
        datePicker.setText(dateFormat.format(calendar.getTime()));
    }
    public void getInitValues(){
        initAmnt = Float.valueOf(amntEt.getText().toString().trim());
        latestDate = datePicker.getText().toString();
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
