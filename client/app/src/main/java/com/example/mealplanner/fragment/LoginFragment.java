package com.example.mealplanner.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mealplanner.R;

public class LoginFragment extends Fragment {

    EditText usernameEt;
    EditText passwordEt;
    TextView signupTv;
    Button loginBtn;

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
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container,MainFragment.class,null).commit();
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