package com.example.mealplanner.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.Pantry;


public class PantryIngredientsFragment extends Fragment {
    private Pantry pantry;
    public PantryIngredientsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        pantry = (Pantry) bundle.getSerializable("pantry");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry_ingredients, container, false);
        return view;
    }
}