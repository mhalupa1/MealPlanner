package com.example.mealplanner.wrapper;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mealplanner.model.GenericIngredient;

public class GenericIngredientListWrapper implements Parcelable, Comparable<GenericIngredientListWrapper> {

    private GenericIngredient genericIngredient;
    private boolean selected;

    public GenericIngredientListWrapper(GenericIngredient genericIngredient) {
        this.genericIngredient = genericIngredient;
    }

    public GenericIngredientListWrapper(GenericIngredient genericIngredient, boolean selected) {
        this.genericIngredient = genericIngredient;
        this.selected = selected;
    }

    protected GenericIngredientListWrapper(Parcel in) {
        genericIngredient = in.readParcelable(GenericIngredient.class.getClassLoader());
    }

    public static final Creator<GenericIngredientListWrapper> CREATOR = new Creator<GenericIngredientListWrapper>() {
        @Override
        public GenericIngredientListWrapper createFromParcel(Parcel in) {
            return new GenericIngredientListWrapper(in);
        }

        @Override
        public GenericIngredientListWrapper[] newArray(int size) {
            return new GenericIngredientListWrapper[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(genericIngredient, flags);
    }

    public GenericIngredient getGenericIngredient() {
        return genericIngredient;
    }

    public void setGenericIngredient(GenericIngredient genericIngredient) {
        this.genericIngredient = genericIngredient;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int compareTo(GenericIngredientListWrapper o) {
        return this.getGenericIngredient().getName().compareTo(o.getGenericIngredient().getName());
    }
}
