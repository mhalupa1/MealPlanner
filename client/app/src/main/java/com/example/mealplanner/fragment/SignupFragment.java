package com.example.mealplanner.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
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

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SignupFragment extends Fragment {

    EditText usernameEt;
    EditText passwordEt;
    TextView signinInsteadEt;
    Button signupBtn;

    APIService service;
    Context context;


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        APIClient APIClient = new APIClient();
        Retrofit retrofit = APIClient.getClient();
        service = retrofit.create(APIService.class);
        context = getContext();

        usernameEt = view.findViewById(R.id.usernameSignupEt);
        passwordEt = view.findViewById(R.id.passwordSignupEt);
        signinInsteadEt = view.findViewById(R.id.signin_instead_tv);
        signupBtn = view.findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(signupBtnListener);

        signinInsteadEt.setOnClickListener(signinInsteadListener);
        return view;
    }

    View.OnClickListener signinInsteadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container, LoginFragment.class, null).commit();
        }
    };

    View.OnClickListener signupBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String username = usernameEt.getText().toString();
            String password = passwordEt.getText().toString();
            Call<ResponseBody> signupCall = service.signup(username, password);

            signupCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        User user = gson.fromJson(gson.toJson(response.body()), User.class);
                        UserData.setUser(user);
                        Toast.makeText(context, "Signup success!",
                                Toast.LENGTH_LONG).show();
                        FragmentManager fm = getParentFragmentManager();
                        fm.beginTransaction().replace(R.id.fragment_container, LoginFragment.class, null).commit();
                    } else {
                        //TODO: ne radi bas kak spada
                        Toast.makeText(context, response.body() != null ? response.body().toString() : "Something went wrong",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("DEBUG", "Signup failed");
                }
            });
        }
    };
}