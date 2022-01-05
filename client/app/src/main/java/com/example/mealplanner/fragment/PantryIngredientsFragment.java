package com.example.mealplanner.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.adapter.PantryIngredientAdapter;
import com.example.mealplanner.model.Category;
import com.example.mealplanner.model.GenericIngredient;
import com.example.mealplanner.model.Ingredient;
import com.example.mealplanner.model.MeasuringUnit;
import com.example.mealplanner.model.Pantry;
import com.example.mealplanner.model.PantryIngredient;
import com.example.mealplanner.model.User;
import com.example.mealplanner.service.APIClient;
import com.example.mealplanner.service.APIService;
import com.example.mealplanner.wrapper.PantryIngredientWrapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PantryIngredientsFragment extends Fragment {
    private Pantry pantry;
    private APIService service;
    private ArrayList<PantryIngredient> pantryArrayList = new ArrayList<PantryIngredient>();
    private RecyclerView recyclerView;
    private ArrayList<PantryIngredientWrapper> wrapperArrayList = new ArrayList<>();

    public PantryIngredientsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        pantry = (Pantry) bundle.getSerializable("pantry");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        PantryIngredient p1 = new PantryIngredient(1,LocalDate.parse("2020-01-08"),new BigDecimal(100),new Pantry(1,"pantry",new User(1,"aaaa", "aaaa")),
                new Ingredient(2, "Milka Noisette",new BigDecimal(300),"22222222",new GenericIngredient(2, "chocolate",new MeasuringUnit(1,"g",100.0), new Category(2,"Vegetables"))));
        PantryIngredient p2 = new PantryIngredient(1,LocalDate.parse("2020-01-08"),new BigDecimal(300),new Pantry(1,"pantry",new User(1,"aaaa", "aaaa")),
                new Ingredient(2, "Milka Oreo",new BigDecimal(300),"22222222",new GenericIngredient(2, "chocolate",new MeasuringUnit(1,"g",100.0), new Category(2,"Vegetables"))));
        PantryIngredient p3 = new PantryIngredient(1,LocalDate.parse("2020-01-08"),new BigDecimal(1),new Pantry(1,"pantry",new User(1,"aaaa", "aaaa")),
                new Ingredient(2, null,new BigDecimal(300),"22222222",new GenericIngredient(2, "milk",new MeasuringUnit(1,"L",1.0), new Category(2,"Vegetables"))));
        PantryIngredient p4 = new PantryIngredient(1,LocalDate.parse("2020-01-08"),new BigDecimal(2),new Pantry(1,"pantry",new User(1,"aaaa", "aaaa")),
                new Ingredient(2, "Dukat mlijeko 2.8%",new BigDecimal(300),"22222222",new GenericIngredient(2, "milk",new MeasuringUnit(1,"L",1.0), new Category(2,"Vegetables"))));

        pantryArrayList.add(p1);
        pantryArrayList.add(p2);
        pantryArrayList.add(p3);
        pantryArrayList.add(p4);
        mapIngredients();


        APIClient APIClient = new APIClient();
        Retrofit retrofit = APIClient.getClient();
        service = retrofit.create(APIService.class);
        Call<List<PantryIngredient>> pantryIngCall = service.getPantryIngredients(pantry.getId());
        pantryIngCall.enqueue(new Callback<List<PantryIngredient>>() {
            @Override
            public void onResponse(Call<List<PantryIngredient>> call, Response<List<PantryIngredient>> response) {
                if(response.isSuccessful() && response.body() != null){
                    pantryArrayList.addAll(response.body());
                    //mapIngredients();
                }
            }

            @Override
            public void onFailure(Call<List<PantryIngredient>> call, Throwable t) {

            }
        });

        View view = inflater.inflate(R.layout.fragment_pantry_ingredients, container, false);
        FloatingActionButton addPantryIngredientBtn = view.findViewById(R.id.addPantryIngredientBtn);
        addPantryIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container,IngredientListFragment.class,null).commit();
            }
        });
        recyclerView = view.findViewById(R.id.main_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PantryIngredientAdapter adapter = new PantryIngredientAdapter(wrapperArrayList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void mapIngredients(){
        HashMap<String, ArrayList<PantryIngredient>> ingredientMap = new HashMap<>();
        for(PantryIngredient pantryIngredient : pantryArrayList){
            String key = pantryIngredient.getIngredient().getGenericIngredient().getName();
            if(ingredientMap.containsKey(key)){
                ingredientMap.get(key).add(pantryIngredient);
            }else{
                ArrayList<PantryIngredient> arrayList = new ArrayList<>();
                arrayList.add(pantryIngredient);
                ingredientMap.put(key, arrayList);

            }
        }
        for (Map.Entry<String, ArrayList<PantryIngredient>> entry : ingredientMap.entrySet()) {
            wrapperArrayList.add(new PantryIngredientWrapper(entry.getValue(), entry.getKey()));
        }

    }
}