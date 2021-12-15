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


public class SignupFragment extends Fragment {

    EditText usernameEt;
    EditText passwordEt;
    TextView signinInsteadEt;
    Button signupBtn;

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
        View view =inflater.inflate(R.layout.fragment_signup, container, false);
        usernameEt = view.findViewById(R.id.usernameSignupEt);
        passwordEt = view.findViewById(R.id.passwordSignupEt);
        signinInsteadEt = view.findViewById(R.id.signin_instead_tv);
        signupBtn = view.findViewById(R.id.signupBtn);


        signinInsteadEt.setOnClickListener(signinInsteadListener);
        return view;
    }

    View.OnClickListener signinInsteadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container,LoginFragment.class,null).commit();
        }
    };

}