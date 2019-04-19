package com.library.depending.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.library.depending.broadcast.NetBroadcastReceiver;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\12\11 0011 14:14
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class SaveDataUtils {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public static final String SAVE_PERSON = "save_person";
    public static final String SAVE_VERSION = "save_version";
    public static final String KEY_DATA = "key_data";
    private static SaveDataUtils saveDataUtils;

    /**
     * 单例模式
     */
    public static SaveDataUtils getInstance() {
        if (saveDataUtils == null) {
            saveDataUtils = new SaveDataUtils();
        }
        return saveDataUtils;
    }

    /**
     * @param activity
     * @param name
     */
    public void  init(Activity activity, String name) {
        sp = activity.getSharedPreferences(name, Activity.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 首选项保存
     */
    public void setObject( String key,Object strObject) throws Exception {
        editor.putString(key,new Gson().toJson(strObject));
    }

    /**
     * 首选项保存
     */
    public void setInt(String key, int i) {
        editor.putInt(key, i);
    }

    /**
     * 首选项提交
     */
    public void commit() {
        editor.commit();
    }

    /**
     * 取出数据
     *
     * @param key
     * @return
     * @throws Exception
     */
    public Object getObject(String key) throws Exception {
        return sp.getString(key, null);
    }

    /**
     * 首选项保存
     */
    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    /**
     * 清空数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

}
