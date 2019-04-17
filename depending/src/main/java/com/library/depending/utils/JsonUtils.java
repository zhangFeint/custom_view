package com.library.depending.utils;


import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//    String message1 = "{\"key\":\"valve\",\"key\":\"valve\",\"key\":\"valve\"}";
//    String message2 = "[{\"key\":\"value\"},{\"key\":\"value\"},{\"key\":\"value\"}]";
//    String message3 = "key:value";
//                System.out.println(JsonUtils.getInstance().estimate(message1));
//                System.out.println(JsonUtils.getInstance().estimate(message2));
//                System.out.println(JsonUtils.getInstance().estimate(message3));

/**
 * @author：zhangerpeng 版本：
 * 日期：2019/4/15 22:54
 * 描述：
 */
public class JsonUtils {
    private static JsonUtils jsonUtils;


    /**
     * 单例模式
     */
    public static JsonUtils getInstance() {
        if (jsonUtils == null) {
            jsonUtils = new JsonUtils();
        }
        return jsonUtils;
    }

    /**
     * 判断json 类型
     * 0、JSONObject格式 1、JSONArray格式 2、String格式
     *
     * @param message "{\"key\":\"valve\",\"key\":\"valve\",\"key\":\"valve\"}";
     */
    public int estimate(String message) {
        try {
            Object json = new JSONTokener(message).nextValue();
            if (json instanceof JSONObject) {
                return 0;
            } else if (json instanceof JSONArray) {
                return 1;
            } else {
                return 2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Map<String, String> getJsonMap(String str) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(str);
            Iterator<String> it = jsonObject.keys();
            while (it.hasNext()) {
                String key = it.next();// 获得key
                String value = jsonObject.getString(key);
                map.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return map;
        }
        return map;
    }

    public String getJson(Object src) {

        return new Gson().toJson(src);
    }


}
