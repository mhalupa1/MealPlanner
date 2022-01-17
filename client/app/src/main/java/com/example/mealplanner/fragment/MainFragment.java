package com.example.mealplanner.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealplanner.R;
import com.example.mealplanner.model.Category;
import com.example.mealplanner.model.GenericIngredient;
import com.example.mealplanner.service.APIClient;
import com.example.mealplanner.service.APIService;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Comparator;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_main, container, false);
        SharedPreferences pref = getContext().getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);

        if(pref.contains("categories") && pref.contains("genericIngredients")){
            boolean confirmed = pref.getBoolean("confirmed", false);
            pref.edit().remove("confirmed").apply();
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,PantryFragment.class,null, confirmed ? "afterConfirm" : null).commit();
        }else{
            Gson gson = new Gson();
            APIClient APIClient = new APIClient();
            Retrofit retrofit = APIClient.getClient();
            service = retrofit.create(APIService.class);

            Call<List<Category>> categoryCall = service.getCategories();
            categoryCall.enqueue(new Callback<List<Category>>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    if(response.isSuccessful()){
                        List<Category> categories = response.body();
                        categories.sort(Comparator.comparing(Category::getName));
                        String cat = gson.toJson(categories);
                        pref.edit().putString("categories", gson.toJson(categories)).apply();
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            Call<List<GenericIngredient>> genIngCall = service.getGenericIngredients();

            genIngCall.enqueue(new Callback<List<GenericIngredient>>() {
                @Override
                public void onResponse(Call<List<GenericIngredient>> call, Response<List<GenericIngredient>> response) {
                    if(response.isSuccessful()){
                        List<GenericIngredient> ingredients = response.body();
                        pref.edit().putString("genericIngredients", gson.toJson(ingredients)).apply();
                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,PantryFragment.class,null).commit();
                    }
                }

                @Override
                public void onFailure(Call<List<GenericIngredient>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }




        return view;
    }
}