package com.example.mealplanner.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageMethods {
    public static void changeLanguage(Context context,String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        SharedPreferences pref = context.getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);
        pref.edit().putString("language",language).apply();
    }
    public static void loadLanguage(Context context){
        SharedPreferences pref = context.getSharedPreferences("mealPlanner", Context.MODE_PRIVATE);
        if(pref.contains("language")){
            String language = pref.getString("language","");
            changeLanguage(context,language);
        }
    }
}
