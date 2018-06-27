package com.feitian.login;

import android.util.Log;


/**
 * @author yemint
 * Log统一管理类
 */
public class CnLogUtils {

    private CnLogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("CnLogUtils cannot be instantiated");
    }

//    public static boolean isDebug = AppConstants.isDebug;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    public static boolean isDebug = AppConstants.isDebug;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "FTLogin..DEBUG..已开启";

    // 下面是传入自定义tag的函数
    public static void i(String methodName, String msg) {
        if (isDebug)
            Log.i(TAG, canvs(methodName, msg));
    }

    public static void d(String methodName, String msg) {
        if (isDebug)
            Log.d(TAG, canvs(methodName, msg));
    }

    public static void e(String methodName, String msg) {
        if (isDebug)
            Log.e(TAG, canvs(methodName, msg));
    }

    public static String canvs(String name, String msg) {
        String a = "|||||\n";
        String b = "|----------------------------------------------------------------------------------------------------|\n|";
        String c = "\n|----------------------------------------------------------------------------------------------------|\n|";
        String d = "\n|----------------------------------------------------------------------------------------------------|";
        String last = a + b + "--------->" + name + c + "--------->" + msg + d;
        return last;
    }


}
