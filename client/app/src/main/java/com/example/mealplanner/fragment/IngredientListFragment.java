package com.example.mealplanner.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mealplanner.R;
import com.example.mealplanner.adapter.CategoryAdapter;
import com.example.mealplanner.global.LanguageMethods;
import com.example.mealplanner.global.UserData;
import com.example.mealplanner.model.Category;
import com.example.mealplanner.model.GenericIngredient;
import com.example.mealplanner.model.Ingredient;
import com.example.mealplanner.model.Pantry;
import com.example.mealplanner.model.PantryIngredient;
import com.example.mealplanner.model.util.IngredientConfirmItem;
import com.example.mealplanner.service.APIClient;
import com.example.mealplanner.service.APIService;
import com.example.mealplanner.wrapper.CategoryListWrapper;
import com.example.mealplanner.wrapper.GenericIngredientListWrapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class IngredientListFragment extends Fragment {

    SharedPreferences pref;
    RecyclerView recyclerView;
    FloatingActionButton scanBtn;
    EditText searchEt;

    final private Gson gson = new Gson();
    private APIService service;

    List<GenericIngredient> genericIngredients;
    List<Ingredient> ingredientsList;
    List<Ingredient> fullIngList;
    Pantry selectedPantry;

    private CategoryAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ingredient_list_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ingListConfirm) {
            handleConfirmItemClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pref = getContext().getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        LanguageMethods.loadLanguage(getContext());
        APIClient APIClient = new APIClient();
        Retrofit retrofit = APIClient.getClient();
        service = retrofit.create(APIService.class);

        Bundle bundle = getArguments();
        selectedPantry = (Pantry) bundle.getSerializable("pantry");

        List<Category> categories = Arrays.asList(gson.fromJson(pref.getString("categories", ""), Category[].class));
        genericIngredients = Arrays.asList(gson.fromJson(pref.getString("genericIngredients", ""), GenericIngredient[].class));


        List<CategoryListWrapper> categoryWrappers = new LinkedList<>();
        List<GenericIngredientListWrapper> genericIngredientWrappers = new LinkedList<>();
        for (GenericIngredient ing : genericIngredients) {
            genericIngredientWrappers.add(new GenericIngredientListWrapper(ing));
        }

        for (Category category : categories) {
            List<GenericIngredientListWrapper> catIngList = new LinkedList<>();
            /*List<GenericIngredientListWrapper> catIngList = genericIngredientWrappers.stream().
                    filter(ing -> ing.getGenericIngredient().getCategory().getId() == category.getId())
                    .collect(Collectors.toList());*/
            for (GenericIngredientListWrapper wrapper : genericIngredientWrappers) {
                if (wrapper.getGenericIngredient().getCategory().getId() == category.getId()) {
                    catIngList.add(wrapper);
                }
            }
            catIngList.sort(Comparator.comparing(o -> o.getGenericIngredient().getName()));
            CategoryListWrapper wrapper = new CategoryListWrapper(new Category(category.getId(), category.getName()), catIngList);
            categoryWrappers.add(wrapper);

        }

        adapter = new CategoryAdapter(categoryWrappers, inflater, getContext());
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        scanBtn = view.findViewById(R.id.scanBarcodeBtn);
        searchEt = view.findViewById(R.id.ingListSearch);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView = view.findViewById(R.id.recyclerViewIngList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(1, 0);



        scanBtn.setOnClickListener(scanBtnListener);

        searchEt.addTextChangedListener(searchListener);
        return view;
    }


    View.OnClickListener scanBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, BarcodeFragment.class, null).commit();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleConfirmItemClick() {

        List<IngredientConfirmItem> checkedIngredients = adapter.onDestroy();
        if (checkedIngredients.isEmpty()) {
            Toast.makeText(getContext(), "Add some ingredients first", Toast.LENGTH_LONG).show();
            return;
        }
        Log.d("checkedIngredients", gson.toJson(checkedIngredients));
        Call<List<Ingredient>> call = service.getIngredientsByGenericId(checkedIngredients.stream()
                                                                .map(i -> i.getIngredient().getId()).collect(Collectors.toList()));
        List<PantryIngredient> pantryIngredients = new LinkedList<>();

        fullIngList = new LinkedList<>();


        call.enqueue(new Callback<List<Ingredient>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                if (response.isSuccessful()) {
                    ingredientsList = response.body();
                    fullIngList.addAll(ingredientsList);
                    for(Ingredient ing : ingredientsList){
                        IngredientConfirmItem confirmItem = checkedIngredients.stream()
                                                                              .filter(i -> i.getIngredient().getId() == ing.getGenericIngredient().getId())
                                                                              .findFirst().get();;
                        PantryIngredient pantryIngredient = new PantryIngredient(confirmItem.getDate(), BigDecimal.valueOf(confirmItem.getAmount()),selectedPantry, ing);
                        pantryIngredients.add(pantryIngredient);
                    }
                    pref.edit().putString("checkedIngredients", gson.toJson(pantryIngredients)).apply();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, ConfirmIngredientsFragment.class, null).commit();
                }
            }

            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    TextWatcher searchListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();

            //search
            List<GenericIngredient> tempIngList = genericIngredients.stream().filter(i -> i.getName().toLowerCase().contains(text.toLowerCase()))
                                                .collect(Collectors.toList());
            //Log.d("tempIngList", new Gson().toJson(tempIngList));

            //get categories of matches items
            List<Category> tempCatList = tempIngList.stream().map(i -> i.getCategory())
                                        .collect(Collectors.toSet()).stream().sorted().collect(Collectors.toList());

            Log.d("tempCatList", new Gson().toJson(tempCatList));


            List<CategoryListWrapper> wrapperList = new LinkedList<>();

            for(Category category : tempCatList){
                //create wrapper objects for adapter
                List<GenericIngredientListWrapper> categoryIngList = tempIngList.stream()
                        .filter(i -> i.getCategory().getId() == category.getId())
                        .map(i -> new GenericIngredientListWrapper(i, true)).sorted().collect(Collectors.toList());


                //replace category wrapper with new wrapper with filtered list
                CategoryListWrapper newWrapper = new CategoryListWrapper(category, categoryIngList);
                //Log.d("categoryIngList", new Gson().toJson(categoryIngList));
                wrapperList.add(newWrapper);

            }

            adapter.getGroups().clear();
            List<CategoryListWrapper> dataset = (List<CategoryListWrapper>) adapter.getGroups();
            dataset.addAll(wrapperList);

            Log.d("debug", String.valueOf(wrapperList.size()));
            adapter.notifyGroupDataChanged();
            adapter.notifyDataSetChanged();
        }
    };
}

