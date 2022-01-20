package com.example.mealplanner.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.mealplanner.R;
import com.example.mealplanner.adapter.PantryAdapter;
import com.example.mealplanner.global.LanguageMethods;
import com.example.mealplanner.model.Pantry;
import com.example.mealplanner.model.User;
import com.example.mealplanner.service.APIClient;
import com.example.mealplanner.service.APIService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PantryFragment extends Fragment {

    private APIService service;
    private RecyclerView recyclerView;
    private ArrayList<Pantry> pantryArrayList = new ArrayList<Pantry>();
    private FloatingActionButton addPantryBtn;
    private SharedPreferences pref;
    private User user;
    private PantryAdapter pantryAdapter;

    public PantryFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pantryArrayList.clear();
        LanguageMethods.loadLanguage(getContext());
        getActivity().setTitle(getContext().getResources().getString(R.string.pantries));
        APIClient APIClient = new APIClient();
        Retrofit retrofit = APIClient.getClient();
        service = retrofit.create(APIService.class);
        pref = getContext().getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        user = gson.fromJson(pref.getString("user",null), User.class);

        View view = inflater.inflate(R.layout.fragment_pantry, container, false);
        addPantryBtn = view.findViewById(R.id.addPantryBtn);
        recyclerView = view.findViewById(R.id.recyclerViewPantryList);
        recyclerView.setHasFixedSize(true);
        pantryAdapter = new PantryAdapter(pantryArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(pantryAdapter);
        pantryAdapter.setOnItemClickListener(new PantryAdapter.OnPantryClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Pantry pantry = pantryArrayList.get(position);
                PantryIngredientsFragment fragment = new PantryIngredientsFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("pantry", pantry);
                fragment.setArguments(bundle);
                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container,fragment,null).addToBackStack(null).commit();
            }
        });
        pantryAdapter.setOnLongItemClickListener(new PantryAdapter.OnLongPantryClickListener() {
            @Override
            public void onLongItemClick(View view, int position) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.deletePantry){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle(getContext().getResources().getString(R.string.delete_confirm));
                            builder.setMessage(getContext().getResources().getString(R.string.delete_question));
                            builder.setPositiveButton(getContext().getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Call<ResponseBody> deletePantryCall = service.deletePantry(pantryArrayList.get(position).getId());
                                    deletePantryCall.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if(response.isSuccessful()){
                                                pantryArrayList.remove(position);
                                                pantryAdapter.notifyItemRemoved(position);
                                            }else{
                                                Toast.makeText(getContext(), getContext().getResources().getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });

                                }
                            });
                            builder.setNeutralButton(getContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        addPantryBtn.setOnClickListener(addPantryBtnListener);

        Call<List<Pantry>> getUserPantriesCall = service.getUserPantries(user.getId());
        getUserPantriesCall.enqueue(new Callback<List<Pantry>>() {
            @Override
            public void onResponse(Call<List<Pantry>> call, Response<List<Pantry>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    pantryArrayList.addAll(response.body());
                    pantryAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Pantry>> call, Throwable t) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.check_network), Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }

    View.OnClickListener addPantryBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            final View customLayout = getLayoutInflater().inflate(R.layout.pantry_dialog,null);
            builder.setView(customLayout);
            builder.setTitle(getContext().getResources().getString(R.string.add_pantry));
            builder.setPositiveButton(getContext().getResources().getString(R.string.add), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText addPantry = customLayout.findViewById(R.id.addPantryEditTxt);
                    String name = addPantry.getText().toString().trim();
                    Pantry pantry = new Pantry(name,user);
                    Call<Pantry> savePantryCall = service.savePantry(pantry);
                    savePantryCall.enqueue(new Callback<Pantry>() {
                        @Override
                        public void onResponse(Call<Pantry> call, Response<Pantry> response) {
                            if(response.isSuccessful() && response.body() != null){
                                Pantry pantry = response.body();
                                pantryArrayList.add(pantry);
                                pantryAdapter.notifyItemInserted(pantryArrayList.indexOf(pantry));
                            }else{
                                Toast.makeText(getContext(), getContext().getResources().getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Pantry> call, Throwable t) {

                        }
                    });


                }
            });
            builder.setNeutralButton(getContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });


            AlertDialog dialog = builder.create();
            dialog.show();

        }
    };
}