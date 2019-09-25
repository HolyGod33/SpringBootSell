package com.imooc.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 11:09 2019/8/3
 */
public class JsonUtil {
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        return gson.toJson(object);
    }
}
