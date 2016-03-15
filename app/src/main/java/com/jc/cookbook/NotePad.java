package com.jc.cookbook;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by tconan on 16/3/15.
 */
public class NotePad {

    // 单例模式
    private static NotePad notePad;

    public static NotePad getInstance(Context context, String name) {
        if (notePad==null) {
            notePad = new NotePad(context, name);
        }
        return notePad;
    }

    // 笔记本实现
    private Context context;
    private String name;

    private NotePad(Context context, String name) {
        this.context = context;
        this.name = name;
    }

    public void putInt(String var1, int var2) {
        getEditor().putInt(var1, var2);
        commitEditor();
    }

    public void putString(String var1, String var2) {
        getEditor().putString(var1, var2);
        commitEditor();
    }

    public void putStringSet(String var1, Set<String> var2) {
        getEditor().putStringSet(var1, var2);
        commitEditor();
    }

    public void putLong(String var1, Long var2) {
        getEditor().putLong(var1, var2);
        commitEditor();
    }

    public void putFloat(String var1, Float var2) {
        getEditor().putFloat(var1, var2);
        commitEditor();
    }

    public void putBoolean(String var1, Boolean var2) {
        getEditor().putBoolean(var1, var2);
        commitEditor();
    }

    public boolean contains(String var1) {
        return getSharedPreferences().contains(var1);
    }



    public String getString(String var1, String var2) {
        return getSharedPreferences().getString(var1, var2);
    }

    public Set<String> getStringSet(String var1, Set<String> var2) {
        return getSharedPreferences().getStringSet(var1, var2);
    }

    public int getInt(String var1, int var2) {
        return getSharedPreferences().getInt(var1, var2);
    }

    public long getLong(String var1, long var2) {
        return getSharedPreferences().getLong(var1, var2);
    }

    public float getFloat(String var1, float var2) {
        return getSharedPreferences().getFloat(var1, var2);
    }

    public boolean getBoolean(String var1, boolean var2) {
        return getSharedPreferences().getBoolean(var1, var2);
    }


    // 基础单例
    private SharedPreferences sp;
    private SharedPreferences getSharedPreferences() {
        if (sp==null) {
            sp = context.getSharedPreferences(this.name, context.MODE_PRIVATE);
        }
        return sp;
    }

    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor getEditor() {
        if (editor==null) {
            SharedPreferences sp = getSharedPreferences();
            //存入数据
            editor = sp.edit();
        }
        return editor;
    }

    // 提交
    private void commitEditor() {
        editor.commit();
    }

}
