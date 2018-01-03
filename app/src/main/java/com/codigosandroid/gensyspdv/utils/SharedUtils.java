package com.codigosandroid.gensyspdv.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Tiago on 27/12/2017.
 */

public class SharedUtils {

    public static String getString(Context context, String key) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");

    }

    public static boolean isBoolean(Context context, String key) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);

    }

    // Temporário até atualização de biblioteca utilitária
    public static void setBoolean(Context context, String flag, boolean on) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(flag, on);
        editor.apply();
    }

    // Temporário até atualização de biblioteca utilitária
    public static boolean getBoolean(Context context, String flag) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(flag, false);
    }

}
