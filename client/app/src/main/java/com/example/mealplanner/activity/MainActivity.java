package com.example.mealplanner.activity;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.mealplanner.R;
import com.example.mealplanner.fragment.ConfirmIngredientsFragment;
import com.example.mealplanner.fragment.LoginFragment;
import com.example.mealplanner.fragment.MainFragment;
import com.example.mealplanner.fragment.PantryFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ConfirmIngredientsFragment confirmFragment = (ConfirmIngredientsFragment) getSupportFragmentManager().findFragmentByTag("confirm");
                FragmentManager fm = getSupportFragmentManager();
                if (confirmFragment != null && confirmFragment.isVisible()) {
                    pref.edit().putBoolean("backConfirm", true).apply();

                    int count = fm.getBackStackEntryCount();
                    int ingListFmCount = 0;
                    for (int i = count - 1; count >= 0; --i) {
                        FragmentManager.BackStackEntry entry = fm.getBackStackEntryAt(i);
                        if (entry.getName() != null && entry.getName().equals("ingList")) {
                            ingListFmCount++;
                        } else {
                            break;
                        }
                    }
                    for (int i = 0; i < ingListFmCount; ++i) {
                        fm.popBackStack();
                    }
                    return;
                }
                PantryFragment pantryFragment = (PantryFragment) getSupportFragmentManager().findFragmentByTag("afterConfirm");

                if(pantryFragment!=null && pantryFragment.isVisible()){
                    int count = fm.getBackStackEntryCount();
                    Log.d("beforeCount", String.valueOf(count));
                    for(int i = 0; i<count; ++i){
                        fm.popBackStack();
                    }
                    Log.d("afterCount", String.valueOf(count));
                    return;
                }
                fm.popBackStack();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, LoginFragment.class, null).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    Log.d("focus", "touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}