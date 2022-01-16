package com.example.mealplanner.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mealplanner.R;
import com.example.mealplanner.adapter.ConfirmIngredientAdapter;
import com.example.mealplanner.global.LanguageMethods;
import com.example.mealplanner.model.PantryIngredient;
import com.example.mealplanner.service.APIClient;
import com.example.mealplanner.service.APIService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmIngredientsFragment extends Fragment {

    RecyclerView recyclerView;
    Button saveBtn;

    ConfirmIngredientAdapter adapter;
    APIService service;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_ingredients, container, false);
        SharedPreferences pref = getContext().getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        LanguageMethods.loadLanguage(getContext());
        recyclerView = view.findViewById(R.id.confirmList);
        saveBtn = view.findViewById(R.id.saveIngredientsBtn);

        service = APIClient.getClient().create(APIService.class);

        String checkedIngredientsStr = pref.getString("checkedIngredients", null);
        List<PantryIngredient> pantryIngredients = new ArrayList<>(Arrays.asList(gson.fromJson(checkedIngredientsStr, PantryIngredient[].class)));

        adapter = new ConfirmIngredientAdapter(pantryIngredients);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(1, 0);

        saveBtn.setOnClickListener(saveBtnListener);

        return view;
    }

    View.OnClickListener saveBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<PantryIngredient> list = adapter.getIngredients();
            Call<List<PantryIngredient>> call = service.saveAllPantryIngredients(list);

            call.enqueue(new Callback<List<PantryIngredient>>() {
                @Override
                public void onResponse(Call<List<PantryIngredient>> call, Response<List<PantryIngredient>> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getContext(),"Success!", Toast.LENGTH_LONG).show();
                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, MainFragment.class,null).commit();
                    }
                }

                @Override
                public void onFailure(Call<List<PantryIngredient>> call, Throwable t) {
                    t.printStackTrace();
                }
            });


        }
    };
}