package com.example.mealplanner.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import com.example.mealplanner.global.LanguageMethods;
import com.example.mealplanner.global.UserData;
import com.example.mealplanner.model.User;
import com.example.mealplanner.service.APIClient;
import com.example.mealplanner.service.APIService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Locale;

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
    TextView changeLanguageTv;
    APIService service;
    Context context;


    public SignupFragment() {
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        APIClient APIClient = new APIClient();
        Retrofit retrofit = APIClient.getClient();
        service = retrofit.create(APIService.class);
        context = getContext();
        LanguageMethods.loadLanguage(context);
        usernameEt = view.findViewById(R.id.usernameSignupEt);
        passwordEt = view.findViewById(R.id.passwordSignupEt);
        signinInsteadEt = view.findViewById(R.id.signin_instead_tv);
        signupBtn = view.findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(signupBtnListener);
        changeLanguageTv = view.findViewById(R.id.changeLanguageTv1);
        changeLanguageTv.setPaintFlags(changeLanguageTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        changeLanguageTv.setOnClickListener(changeLanguageListener);
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
                        User user = null;
                        try {
                            user = gson.fromJson(response.body().string(), User.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        UserData.setUser(user);
                        Toast.makeText(context, getContext().getResources().getString(R.string.signup_success),
                                Toast.LENGTH_LONG).show();
                        FragmentManager fm = getParentFragmentManager();
                        fm.beginTransaction().replace(R.id.fragment_container, LoginFragment.class, null).commit();
                    } else {
                        //TODO: ne radi bas kak spada
                        Toast.makeText(context, response.body() != null ? response.body().toString() : getContext().getResources().getString(R.string.something_wrong),
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("DEBUG", "Signup failed");
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
    View.OnClickListener changeLanguageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(changeLanguageTv.getText().toString().equals("Croatian")){
                LanguageMethods.changeLanguage(getContext(),"hr");
                reload();
            }else{
                LanguageMethods.changeLanguage(getContext(),"en");
                reload();
            }
        }
    };
    private void reload(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fm.beginTransaction().detach(SignupFragment.this).commitNow();
            fm.beginTransaction().attach(SignupFragment.this).commitNow();
        } else {
            fm.beginTransaction().detach(SignupFragment.this).attach(SignupFragment.this).commit();
        }
    }
}