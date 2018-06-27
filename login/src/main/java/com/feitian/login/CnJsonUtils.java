package com.feitian.login;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * json解析工具类  封装的是gson
 *
 * @author yemint
 */
public class CnJsonUtils {
    //线程安全的
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()//打开Export注解，但打开了这个注解,副作用，要转换和不转换都要加注解
//              .serializeNulls()  //是否序列化空值
                .setDateFormat("yyyy-MM-dd HH:mm:ss")//序列化日期格式  "yyyy-MM-dd"
//              .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写
                .setPrettyPrinting() //自动格式化换行
//              .setVersion(1.0)  //需要结合注解使用，有的字段在1。0的版本的时候解析，但0。1版本不解析
                .create();

    }

    //获取gson解析器
    public static Gson getInstance() {
        return GSON;
    }

    //对象转换为json
    public static String toJson(Object object) {
        CnLogUtils.i("JsontilsU:toJson", object.toString());
        return getInstance().toJson(object);
    }

    //JSON转换为对象1--普通类型
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return getInstance().fromJson(json, classOfT);
    }

    //JSON转换为对象-针对泛型的类型
    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }

    public static <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            mList.add(GSON.fromJson(elem, cls));
        }
        return mList;
    }
}
