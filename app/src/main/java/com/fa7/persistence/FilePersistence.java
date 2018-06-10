package com.fa7.persistence;

import android.content.Context;
import android.content.SharedPreferences;

public class FilePersistence {

    private static final  String prefsName = "AppMovies";

    public static void  saveText(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public  static String getText(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsName, 0);
        return sharedPreferences.getString(key, "");
    }
}
