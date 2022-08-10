package com.h.haoyangmaov2;



import android.app.Application;

import java.io.File;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.annotation.Converter;
import rxhttp.wrapper.cahce.CacheMode;
import rxhttp.wrapper.cookie.CookieStore;
import rxhttp.wrapper.ssl.HttpsUtils;

public class RxHttpManager {

    public static void init(Application context) {
        File file = new File(context.getExternalCacheDir(), "RxHttpCookie");
        File cacheDir = new File(context.getExternalCacheDir(), "RxHttpCache");
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        OkHttpClient client = new OkHttpClient.Builder()
                .proxy(Proxy.NO_PROXY)
                .cookieJar(new CookieStore(file))
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
                .hostnameVerifier((hostname, session) -> true) //忽略host验证
                .build();

        RxHttpPlugins.init(client)
                .setResultDecoder(s -> {
                    if (SimpleAESKt.checkHexString(s)) {     //解密
                        return SimpleAESKt.simpleAesDecrypt(s);
                    } else {
                        return s;
                        //返回明文
                    }
                })//自定义OkHttpClient对象
                .setCache(cacheDir, 10 * 1024 * 1024, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE, 60 * 1000)
                .setDebug(true)               //是否开启调试模式，开启后，logcat过滤RxHttp，即可看到整个请求流程日志
                .setOnParamAssembly(p -> {
//                    p.add("totp", TOTP.generateMyTOTP());
                    return p; //添加公共请求头
                });
    }


}