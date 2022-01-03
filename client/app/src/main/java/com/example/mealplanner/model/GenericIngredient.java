package com.example.mealplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GenericIngredient implements Parcelable {

    private int id;
    private String name;
    private MeasuringUnit measuringUnit;
    private Category category;

    public GenericIngredient(int id, String name, MeasuringUnit measuringUnit, Category category) {
        this.id = id;
        this.name = name;
        this.measuringUnit = measuringUnit;
        this.category = category;
    }


    protected GenericIngredient(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<GenericIngredient> CREATOR = new Creator<GenericIngredient>() {
        @Override
        public GenericIngredient createFromParcel(Parcel in) {
            return new GenericIngredient(in);
        }

        @Override
        public GenericIngredient[] newArray(int size) {
            return new GenericIngredient[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MeasuringUnit getMeasuringUnit() {
        return measuringUnit;
    }

    public void setAmountType(MeasuringUnit measuringUnit) {
        this.measuringUnit = measuringUnit;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
