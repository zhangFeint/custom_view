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

    /**
     * 单例模式
     */
    public static JsonUtils getInstance() {
        if (jsonUtils == null) {
            jsonUtils = new JsonUtils();
        }
        return jsonUtils;
    }

    public void estimate(String message) {
        try {
            Object json = new JSONTokener(message).nextValue();
            if (json instanceof JSONObject) {
                System.out.println("数据是JSONObject");
            } else if (json instanceof JSONArray) {
                System.out.println("数据是JSONArray");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getJson() throws JSONException {
        String str = "{\n" +
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
        JSONObject jsonObject = new JSONObject(str);
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();// 获得key
            String value = jsonObject.getString(key);
            System.out.println("key: " + key + ",value:" + value);
        }
    }

}
