package com.h.haoyangmaov2;


import android.util.Log;


/**
 * Created by lwb on 2017/12/25.
 * 日志封装
 */

public class Logs {
    private static final int LOG_MAXLENGTH = 2000;
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    /**
     * 判断是否可以调试
     *
     * @return
     */
    public static boolean isDebuggable() {
        return true;
    }

    private static String createLog(String log) {
        String buffer = "==" + methodName +
                "(" + className + ":" + lineNumber + ")==:" +
                log;
        return buffer;
    }

    /**
     * 获取文件名、方法名、所在行数
     *
     * @param sElements
     */
    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());

        int strLength = message.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 1000; i++) {
            if (strLength > end) {
                Log.e(className, createLog(message.substring(start, end)));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.e(className, createLog(message.substring(start, strLength)));
                break;
            }
        }
//        Log.e(className, createLog(message));
    }

    public static void i(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

}