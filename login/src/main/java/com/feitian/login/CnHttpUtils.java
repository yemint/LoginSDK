package com.feitian.login;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求工具类
 *
 * @author yemint
 */

public class CnHttpUtils {

    private static volatile CnHttpUtils instance;
    private static final String TAG = "CnHttpUtils";


    private static OkHttpClient okHttpClient;


    public static Handler ler = new Handler();

    private CnHttpUtils() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(AppConstants.DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.SECONDS)
                .readTimeout(AppConstants.DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.SECONDS)
                .writeTimeout(AppConstants.DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 双重检测锁-单例模式
     *
     * @return
     */
    public static CnHttpUtils getInstance() {
        if (instance == null) {
            synchronized (CnHttpUtils.class) {
                if (instance == null) {
                    instance = new CnHttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 网络请求--get请求
     *
     * @param url
     * @param map
     * @param callback
     * @param cls      json实体类
     */
    public void get(String url, Map<String, String> map, final CallBacks callback, final Class cls) {

        //对url和参数做一下拼接处理
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        if (url.contains("?")) {
            //如果？不在最后一位
            if (sb.indexOf("?") != sb.length() - 1) {
                sb.append("&");
            }
        } else {
            sb.append("?");
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        if (sb.indexOf("&") != -1) {
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        CnLogUtils.i(TAG, "get url: " + sb);
        Request request = new Request.Builder()
                .get()
                .url(sb.toString())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                CnLogUtils.e(TAG, "onFailure:" + e.getCause().getStackTrace() + e.getMessage());
                ler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                CnLogUtils.d(TAG, result);
                final Object o = CnJsonUtils.fromJson(result, cls);
                ler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(o);
                    }
                });
            }
        });
    }

    /**
     * 网络请求--post请求
     *
     * @param url
     * @param map
     * @param callback
     * @param cls      json实体类
     */
    public void post(String url, Map<String, String> map, final CallBacks callback, final Class cls) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                CnLogUtils.e(TAG, "onFailure:" + e.getCause().getStackTrace() + e.getMessage());
                ler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                final Object o = CnJsonUtils.getInstance().fromJson(result, cls);
                ler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(o);
                    }
                });
            }
        });
    }

    public interface CallBacks {
        void onSuccess(Object o);

        void onFailure(Exception e);
    }


}