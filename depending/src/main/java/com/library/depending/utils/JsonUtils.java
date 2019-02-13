package com.library.depending.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Iterator;

public class JsonUtils {
    private static JsonUtils jsonUtils;
    //    String message1 = "{\"key\":\"valve\",\"key\":\"valve\",\"key\":\"valve\"}";
//    estimate(message1);
//    String message2 = "[{\"key\":\"value\"},{\"key\":\"value\"},{\"key\":\"value\"}]";
    public static String STR = "{\n" +
            "    \"name\": \"BeJson\",\n" +
            "    \"url\": \"http://www.bejson.com\",\n" +
            "    \"page\": 88,\n" +
            "    \"isNonProfit\": true,\n" +
            "    \"address\": {\n" +
            "        \"street\": \"科技园路.\",\n" +
            "        \"city\": \"江苏苏州\",\n" +
            "        \"country\": \"中国\"\n" +
            "    },\n" +
            "    \"links\": [\n" +
            "        {\n" +
            "            \"name\": \"Google\",\n" +
            "            \"url\": \"http://www.google.com\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"Baidu\",\n" +
            "            \"url\": \"http://www.baidu.com\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SoSo\",\n" +
            "            \"url\": \"http://www.SoSo.com\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

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

    public void getJson(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            Iterator<String> it = jsonObject.keys();
            while (it.hasNext()) {
                String key = it.next();// 获得key
                String value = jsonObject.getString(key);
                System.out.println("key: " + key + ",value:" + value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
