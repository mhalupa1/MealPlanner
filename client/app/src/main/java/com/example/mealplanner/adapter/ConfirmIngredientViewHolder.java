package com.example.mealplanner.adapter;

import android.app.DatePickerDialog;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.PantryIngredient;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class ConfirmIngredientViewHolder extends RecyclerView.ViewHolder {

    TextView nameTv;
    EditText dateEt;
    EditText amountEt;
    TextView amountTypeTv;
    Calendar calendar;

    PantryIngredient ingredient;


    public ConfirmIngredientViewHolder(@NonNull View itemView, ConfirmIngredientAdapter.OnConfirmItemClickListener listener) {
        super(itemView);

        nameTv = itemView.findViewById(R.id.ingredientListItem);
        dateEt = itemView.findViewById(R.id.ingDatePicker);
        amountEt = itemView.findViewById(R.id.amountEt);
        amountTypeTv = itemView.findViewById(R.id.amountTypeTv);
        calendar = Calendar.getInstance();

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onItemClick(view,position);
                        return true;
                    }
                }
                return false;
            }
        });


    }


    protected void setData(PantryIngredient ingredient) {

        this.ingredient = ingredient;
        this.amountTypeTv.setText(ingredient.getIngredient().getGenericIngredient().getMeasuringUnit().getName());
        this.amountEt.setText(ingredient.getAmount().toString());
        if (ingredient.getIngredient().getName() == null) {
            this.nameTv.setText(ingredient.getIngredient().getGenericIngredient().getName());

        } else {
            this.nameTv.setText(ingredient.getIngredient().getName());
        }
        setDateText();


        dateEt.setVisibility(View.VISIBLE);
        amountEt.setVisibility(View.VISIBLE);
        amountTypeTv.setVisibility(View.VISIBLE);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                logDateChange(year, month + 1, day);
                setDateText();
            }
        };

        dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(view.getContext(), date,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });

        amountEt.addTextChangedListener(amountEtListener);
    }

    private void setDateText() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
        if(ingredient.getExpirationDate() == null){
            ingredient.setExpirationDate(LocalDate.now());
        }
        calendar.set(Calendar.YEAR, ingredient.getExpirationDate().getYear());
        calendar.set(Calendar.MONTH, ingredient.getExpirationDate().getMonthValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, ingredient.getExpirationDate().getDayOfMonth());
        dateEt.setText(dateFormat.format(calendar.getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void logDateChange(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        ingredient.setExpirationDate(date);
    }

    TextWatcher amountEtListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            ingredient.setAmount(BigDecimal.valueOf(Double.parseDouble(s.toString())));
        }
    };

}
