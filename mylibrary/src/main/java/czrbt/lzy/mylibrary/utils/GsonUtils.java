package czrbt.lzy.mylibrary.utils;// @author: lzy  time: 2016/08/26.


import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Gson 转换工具
 */
public class GsonUtils {
    public <T>T  fromJson(String str , final Class<? extends T> cls) {
        return (T)(new GsonBuilder().create().fromJson(str,cls));
    }

    public<T>  T fromJson(String str, Type type)  {
        return (T) new GsonBuilder().create().fromJson(str,type);
    }
    public <T>T  fromJson(JsonObject str , final Class<? extends T> cls) {
        return (T)(new GsonBuilder().create().fromJson(str,cls));
    }

    public<T>  T fromJson(JsonObject str, Type type)  {
        return (T) new GsonBuilder().create().fromJson(str,type);
    }
}

