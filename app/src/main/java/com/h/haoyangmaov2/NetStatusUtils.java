package com.h.haoyangmaov2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 *
 */
public class NetStatusUtils {

    /**
     * 无网络
     */
    public static final int NET_STATUS_NO_NET = 0;

    /**
     * wifi网络
     */
    public static final int NET_STATUS_WIFI_NET = 1;

    /**
     * 4G网络
     */
    public static final int NET_STATUS_4G_NET = 4;

    /**
     * 3G网络
     */
    public static final int NET_STATUS_3G_NET = 3;

    /**
     * 2G网络
     */
    public static final int NET_STATUS_2G_NET = 2;

    /**
     * 获取当前的网络状态
     *
     * @param context 上下文
     * @return 网络状态 没有网络-0：WIFI网络-1：4G网络-4：3G网络-3：2G网络-2
     */
    public static int getNetType(Context context) {
        //结果返回值
        int netType = NET_STATUS_NO_NET;
        //获取手机所有连接管理对象
        if(context==null){
            return 0;
        }
        if(context.getSystemService(Context.CONNECTIVITY_SERVICE)==null){
            return 0;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = NET_STATUS_WIFI_NET;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                netType = NET_STATUS_4G_NET;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                netType = NET_STATUS_3G_NET;
                //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                netType = NET_STATUS_2G_NET;
            } else {
                netType = NET_STATUS_2G_NET;
            }
        }
        return netType;
    }

}
