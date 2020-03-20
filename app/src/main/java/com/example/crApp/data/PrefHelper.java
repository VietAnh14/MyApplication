package com.example.crApp.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefHelper {
    private final static String FILE_NAME = "PREF_FILE_NAME";
    private final static String TABLE_KEY = "TABLE_KEY";
    private final static String IS_NORMAL_KEY = "NORMAL_KEY";
    private SharedPreferences sharedPreferences;
    private int tableNumber = 0;
    private Boolean isNormal = null;
    private static PrefHelper prefHelper;

    private PrefHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static PrefHelper getInstance(Context context) {
        if (prefHelper == null) synchronized (PrefHelper.class){
            prefHelper = new PrefHelper(context.getApplicationContext());
        }
        return prefHelper;
    }

    public void saveTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        sharedPreferences.edit().putInt(TABLE_KEY, tableNumber).apply();
    }

    public int getTableNumber() {
        return tableNumber == 0 ? sharedPreferences.getInt(TABLE_KEY, 1) : tableNumber;
    }

    public boolean getIsNormal() {
        return isNormal != null ? isNormal : sharedPreferences.getBoolean(IS_NORMAL_KEY, false);
    }

    public void setIsNormal(boolean isNormal) {
        this.isNormal = isNormal;
        sharedPreferences.edit().putBoolean(IS_NORMAL_KEY, isNormal).apply();
    }
}
