package com.example.mealplanner.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealplanner.R;
import com.example.mealplanner.adapter.CategoryAdapter;
import com.example.mealplanner.model.Category;
import com.example.mealplanner.model.GenericIngredient;
import com.example.mealplanner.wrapper.CategoryListWrapper;
import com.example.mealplanner.wrapper.GenericIngredientListWrapper;
import com.google.gson.Gson;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class IngredientListFragment extends Fragment {

    SharedPreferences pref;
    RecyclerView recyclerView;


    public IngredientListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pref = getContext().getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        List<Category> categories = Arrays.asList(gson.fromJson(pref.getString("categories",null),Category[].class));
        List<GenericIngredient> genericIngredients = Arrays.asList(gson.fromJson(pref.getString("genericIngredients",null), GenericIngredient[].class));


        List<CategoryListWrapper> categoryWrappers = new LinkedList<>();
        List<GenericIngredientListWrapper> genericIngredientWrappers = new LinkedList<>();
        for(GenericIngredient ing : genericIngredients){
            genericIngredientWrappers.add(new GenericIngredientListWrapper(ing));
        }

        for(Category category : categories){
            List<GenericIngredientListWrapper> catIngList = genericIngredientWrappers.stream().
                    filter(ing -> ing.getGenericIngredient().getCategory().getId() == category.getId())
                    .collect(Collectors.toList());
            catIngList.sort(Comparator.comparing(o -> o.getGenericIngredient().getName()));
            CategoryListWrapper wrapper = new CategoryListWrapper(new Category(category.getId(), category.getName()),catIngList);
            categoryWrappers.add(wrapper);

        }

        CategoryAdapter adapter = new CategoryAdapter(categoryWrappers,inflater);
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView = view.findViewById(R.id.recyclerViewIngList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }
}