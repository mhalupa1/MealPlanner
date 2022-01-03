package com.example.mealplanner.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealplanner.R;
import com.example.mealplanner.global.UserData;
import com.example.mealplanner.model.Category;
import com.example.mealplanner.model.GenericIngredient;
import com.example.mealplanner.model.Pantry;
import com.example.mealplanner.model.User;
import com.example.mealplanner.service.APIClient;
import com.example.mealplanner.service.APIService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainFragment extends Fragment {

    APIService service;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_main, container, false);

        APIClient APIClient = new APIClient();
        Retrofit retrofit = APIClient.getClient();
        service = retrofit.create(APIService.class);

        SharedPreferences pref = getContext().getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        Call<List<Category>> categoryCall = service.getCategories();
        categoryCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    pref.edit().putString("categories", gson.toJson(response.body())).apply();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });

        Call<List<GenericIngredient>> genIngCall = service.getGenericIngredients();

        genIngCall.enqueue(new Callback<List<GenericIngredient>>() {
            @Override
            public void onResponse(Call<List<GenericIngredient>> call, Response<List<GenericIngredient>> response) {
                if(response.isSuccessful()){
                    pref.edit().putString("genericIngredients", gson.toJson(response.body())).apply();
                }
            }

            @Override
            public void onFailure(Call<List<GenericIngredient>> call, Throwable t) {

            }
        });

        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,PantryFragment.class,null).commit();


        return view;
    }
}