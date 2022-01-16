package com.example.mealplanner.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplanner.Capture;
import com.example.mealplanner.R;
import com.example.mealplanner.global.LanguageMethods;
import com.example.mealplanner.model.GenericIngredient;
import com.example.mealplanner.model.Ingredient;
import com.example.mealplanner.service.APIClient;
import com.example.mealplanner.service.APIService;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BarcodeFragment extends Fragment {
    APIService service;
    Button scanBtn;
    //TextView barcodeTxt;
    Ingredient scannedIngredient = null;
    //String txt = null;
    SharedPreferences pref;
    ArrayAdapter<String> ingredientAdapter;
    HashMap<String, GenericIngredient> map;
    Context context;
    public BarcodeFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_barcode, container, false);
        context = getContext();
        LanguageMethods.loadLanguage(context);
        APIClient APIClient = new APIClient();
        Retrofit retrofit = APIClient.getClient();
        service = retrofit.create(APIService.class);
        pref = getContext().getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        List<GenericIngredient> genericIngredients = Arrays.asList(gson.fromJson(pref.getString("genericIngredients",""), GenericIngredient[].class));
        map = new HashMap<>();
        for(GenericIngredient i : genericIngredients){
            map.put(i.getName(),i);
        }
        List<String> list = new ArrayList<>(map.keySet());
        ingredientAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, list);
        scanBtn = view.findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.initiateScan();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents() != null){
            String barcode = intentResult.getContents();
            Call<ResponseBody> callBarcode = service.getIngredientByBarcode(barcode);
            callBarcode.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful() && response.body() != null){
                        Gson gson = new Gson();
                        try {
                            scannedIngredient = gson.fromJson(response.body().string(), Ingredient.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(scannedIngredient != null){
                            showProductFoundDialog();
                        }else{
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        showProductNotFoundDialog(barcode);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showProductNotFoundDialog(String barcode){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.product_not_found_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText productNameEt = dialog.findViewById(R.id.productNameEt);
        EditText productQuantityEt = dialog.findViewById(R.id.productQuantityEt);
        TextView quantityUnit = dialog.findViewById(R.id.quantityUnitTv);
        productQuantityEt.setVisibility(View.INVISIBLE);
        quantityUnit.setVisibility(View.INVISIBLE);
        TextView noProductFound = dialog.findViewById(R.id.noProductFoundTv);
        TextPaint paint = noProductFound.getPaint();
        Shader textShader=new LinearGradient(0, 0, paint.measureText(noProductFound.getText().toString()),paint.getTextSize() ,
                new int[]{Color.RED,Color.YELLOW},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        noProductFound.getPaint().setShader(textShader);
        AutoCompleteTextView actv = dialog.findViewById(R.id.productAutoCompleteTv);
        actv.setAdapter(ingredientAdapter);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                scannedIngredient = new Ingredient();
                scannedIngredient.setGenericIngredient(map.get(ingredientAdapter.getItem(i)));
                quantityUnit.setText(scannedIngredient.getGenericIngredient().getMeasuringUnit().getName());
                productQuantityEt.setVisibility(View.VISIBLE);
                quantityUnit.setVisibility(View.VISIBLE);
            }
        });
        Button saveBtn = dialog.findViewById(R.id.saveProductBtn);
        TextView cancelBtn = dialog.findViewById(R.id.cancelProductTv);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                scannedIngredient = null;
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = productNameEt.getText().toString().trim();
                String quantity = productQuantityEt.getText().toString().trim();
                if(name.isEmpty()){
                    Toast.makeText(context,"Enter product name", Toast.LENGTH_SHORT).show();
                    productNameEt.requestFocus();
                }else if(scannedIngredient == null || scannedIngredient.getGenericIngredient() == null){
                    Toast.makeText(context,"Choose product type", Toast.LENGTH_SHORT).show();
                    actv.requestFocus();
                }else if(quantity.isEmpty()){
                    Toast.makeText(context,"Enter quantity", Toast.LENGTH_SHORT).show();
                    productQuantityEt.requestFocus();
                }else{
                    scannedIngredient.setName(name);
                    scannedIngredient.setAmount(new BigDecimal(quantity));
                    scannedIngredient.setBarcode(barcode);
                    Call<Ingredient> saveProductCall = service.saveIngredient(scannedIngredient);
                    saveProductCall.enqueue(new Callback<Ingredient>() {
                        @Override
                        public void onResponse(Call<Ingredient> call, Response<Ingredient> response) {
                            if(response.isSuccessful() && response.body() != null){
                                scannedIngredient = response.body();
                                addToList(scannedIngredient);
                                Toast.makeText(context,"Product added successfully!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Ingredient> call, Throwable t) {
                            Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }
    private void showProductFoundDialog(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.product_found_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView productNameTv = dialog.findViewById(R.id.productNameTv);
        productNameTv.setText(scannedIngredient.getName());
        TextView quantityTv = dialog.findViewById(R.id.productQuantityTv);
        quantityTv.setText(String.valueOf(scannedIngredient.getAmount()));
        TextView quantityUnitTv = dialog.findViewById(R.id.productQuantityUnitTv);
        quantityUnitTv.setText(scannedIngredient.getGenericIngredient().getMeasuringUnit().getName());
        TextView genericIngTv = dialog.findViewById(R.id.genericIngTv);
        genericIngTv.setText(scannedIngredient.getGenericIngredient().getName());
        Button saveBtn = dialog.findViewById(R.id.addProductBtn);
        TextView cancelBtn = dialog.findViewById(R.id.cancelAddProductTv);
        TextView productFound = dialog.findViewById(R.id.productFoundTv);
        TextPaint paint = productFound.getPaint();
        Shader textShader=new LinearGradient(0, 0, paint.measureText(productFound.getText().toString()),paint.getTextSize() ,
                new int[]{Color.GREEN,Color.BLUE},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        productFound.getPaint().setShader(textShader);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToList(scannedIngredient);
                scannedIngredient = null;
                dialog.dismiss();
            }
        });
        dialog.show();
        scannedIngredient = null;
    }
    private void addToList(Ingredient ingredient){

    }
}