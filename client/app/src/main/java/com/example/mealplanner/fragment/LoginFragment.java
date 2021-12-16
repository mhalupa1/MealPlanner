package com.example.mealplanner.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplanner.R;
import com.example.mealplanner.global.UserData;
import com.example.mealplanner.model.User;
import com.example.mealplanner.service.APIClient;
import com.example.mealplanner.service.APIService;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {

    EditText usernameEt;
    EditText passwordEt;
    TextView signupTv;
    Button loginBtn;

    APIService service;
    Context context;

    public LoginFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        context = getContext();

        Long loginTime = context.getSharedPreferences("mealPlanner",Context.MODE_PRIVATE).getLong("loginTime",0);

        //60 minute login session
        if(loginTime != 0 && System.currentTimeMillis()-loginTime < 3600000){
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container,MainFragment.class,null).commit();
        }


        APIClient APIClient = new APIClient();
        Retrofit retrofit = APIClient.getClient();
        service = retrofit.create(APIService.class);


        usernameEt = view.findViewById(R.id.usernameEt);
        passwordEt = view.findViewById(R.id.passwordEt);
        loginBtn = view.findViewById(R.id.loginBtn);
        signupTv = view.findViewById(R.id.signup_instead_tv);
        loginBtn.setOnClickListener(loginBtnListener);
        signupTv.setOnClickListener(signupListener);
        return view;
    }

    View.OnClickListener loginBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String username = usernameEt.getText().toString();
            String password = passwordEt.getText().toString();
            Call<ResponseBody> loginCall =service.login(username,password);

            loginCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful() && response.body()!= null){
                        Gson gson = new Gson();
                        User user = gson.fromJson(gson.toJson(response.body()), User.class);
                        Toast.makeText(context, "Login success!",
                                Toast.LENGTH_LONG).show();
                        SharedPreferences pref = context.getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);
                        pref.edit().putLong("loginTime",System.currentTimeMillis()).apply();
                        FragmentManager fm = getParentFragmentManager();
                        fm.beginTransaction().replace(R.id.fragment_container,MainFragment.class,null).commit();

                    }
                    else {
                        Toast.makeText(context, "Wrong username or password.",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
    };

    View.OnClickListener signupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container,SignupFragment.class,null).commit();
        }
    };




}