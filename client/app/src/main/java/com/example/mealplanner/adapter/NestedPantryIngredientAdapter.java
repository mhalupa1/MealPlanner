package com.example.mealplanner.adapter;

import android.graphics.Color;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.PantryIngredient;

import java.math.BigDecimal;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NestedPantryIngredientAdapter extends RecyclerView.Adapter<NestedPantryIngredientViewHolder> {
    private ArrayList<PantryIngredient> nestedList;
    private OnItemsChangedListener onItemsChangedListener;

    public NestedPantryIngredientAdapter(ArrayList<PantryIngredient> nestedList) {
        this.nestedList = nestedList;
    }

    public interface OnItemsChangedListener {
        void onDateChanged(Editable editable, int p);

        void onAmountChanged(String amount, int p);

        void onLongItemClick(View view, int p);
    }

    public void setOnItemsChangedListener(OnItemsChangedListener onItemsChangedListener) {
        this.onItemsChangedListener = onItemsChangedListener;
    }

    @Override
    public NestedPantryIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_ingredient_list_item, parent, false);
        return new NestedPantryIngredientViewHolder(view, onItemsChangedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedPantryIngredientViewHolder holder, int position) {
        PantryIngredient p = nestedList.get(position);
        if (p.getIngredient().getName() == null) {
            holder.getPantryIngTv().setText(p.getIngredient().getGenericIngredient().getName());
        } else {
            holder.getPantryIngTv().setText(p.getIngredient().getName());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy", Locale.UK);
        String date = formatter.format(p.getExpirationDate());
        float amnt = 0f;
        if (p.getIngredient().getName() == null) {
            double a = (double) p.getIngredient().getGenericIngredient().getMeasuringUnit().getDefaultAmount();
            amnt = (float) a;
        } else {
            amnt = p.getIngredient().getAmount().floatValue();
        }

        holder.setDefaultAmount(amnt);
        holder.getDatePicker().setText(date);
        Calendar temp = Calendar.getInstance();
        setCalendar(holder, temp, p);
        if (holder.calendar.get(Calendar.YEAR) == temp.get(Calendar.YEAR)
                && holder.calendar.get(Calendar.MONTH) == temp.get(Calendar.MONTH)
                && holder.calendar.get(Calendar.DAY_OF_MONTH) == temp.get(Calendar.DAY_OF_MONTH)) {
            holder.getDatePicker().setTextColor(Color.parseColor("#FFA500"));
        } else if (holder.calendar.getTime().before(Calendar.getInstance().getTime())) {
            holder.getDatePicker().setTextColor(Color.RED);
        } else {
            holder.getDatePicker().setTextColor(Color.BLACK);
        }
        holder.getAmntEt().setText(p.getAmount().toString());
        holder.getInitValues();
        holder.getAmountTypeTv().setText(p.getIngredient().getGenericIngredient().getMeasuringUnit().getName());

    }

    @Override
    public int getItemCount() {
        return nestedList.size();
    }

    private void setCalendar(NestedPantryIngredientViewHolder holder, Calendar temp, PantryIngredient p) {
        holder.calendar.set(Calendar.YEAR, p.getExpirationDate().getYear());
        holder.calendar.set(Calendar.MONTH, p.getExpirationDate().getMonthValue() - 1);
        holder.calendar.set(Calendar.DAY_OF_MONTH, p.getExpirationDate().getDayOfMonth());
        holder.calendar.set(Calendar.HOUR, temp.get(Calendar.HOUR));
        holder.calendar.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));
        holder.calendar.set(Calendar.SECOND, temp.get(Calendar.SECOND));
    }
}
