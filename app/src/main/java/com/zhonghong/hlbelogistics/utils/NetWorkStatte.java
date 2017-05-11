package com.zhonghong.hlbelogistics.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/3/7.
 */

public class NetWorkStatte {
    //  检查网络是否连接
    public static boolean networkConnected(Context context)
    {
        if (context!=null)
        {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info  = cm.getActiveNetworkInfo();
            if (info!=null)
            {
                return info.isAvailable();
            }
        }
        return false;
    }
    //检查wifi是否连接
    public static boolean wifiConnected(Context context)
    {
        if (context!=null)
        {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info  = cm.getActiveNetworkInfo();
            if (info!=null)
            {
                if (info.getType()==ConnectivityManager.TYPE_WIFI)
                {
                    return info.isAvailable();
                }

            }
        }
        return false;
    }
    //检查移动网络是否连接
    public static boolean mobileDateConnected(Context context)
    {
        if (context!=null)
        {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info  = cm.getActiveNetworkInfo();
            if (info!=null)
            {
                if (info.getType()==ConnectivityManager.TYPE_MOBILE)
                {
                    return true;
                }

            }
        }
        return false;
    }
}
