package com.asus.intellegent.understandjson;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ASUS on 2017/9/7.
 */

public class Session {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("session", MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    @Nullable
    public String getString(String s, @Nullable String s1) {
        return sharedPreferences.getString(s, s1);
    }

    @Nullable
    public Set<String> getStringSet(String s, @Nullable Set<String> set) {
        return sharedPreferences.getStringSet(s, set);
    }

    public int getInt(String s, int i) {
        return sharedPreferences.getInt(s, i);
    }

    public long getLong(String s, long l) {
        return sharedPreferences.getLong(s, l);
    }

    public float getFloat(String s, float v) {
        return sharedPreferences.getFloat(s, v);
    }

    public boolean getBoolean(String s, boolean b) {
        return sharedPreferences.getBoolean(s, b);
    }

    public boolean contains(String s) {
        return sharedPreferences.contains(s);
    }

    public void putString(String var1, String var2) {
        editor.putString(var1, var2);
    }

    public void putStringSet(String var1, Set<String> var2) {
        editor.putStringSet(var1, var2);
    }

    public void putInt(String var1, int var2) {
        editor.putInt(var1, var2);
    }

    public void putLong(String var1, long var2) {
        editor.putLong(var1, var2);
    }

    public void putFloat(String var1, float var2) {
        editor.putFloat(var1, var2);
    }

    public void putBoolean(String var1, boolean var2) {
        editor.putBoolean(var1, var2);
    }

    public void remove(String var1) {
        editor.remove(var1);
    }

    public void clear() {
        editor.clear();
    }

    public boolean commit() {
        return editor.commit();
    }

    public void apply() {
        editor.apply();
    }
}

