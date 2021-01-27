package com.guosen.common.imageloader

import android.content.Context
import android.net.ConnectivityManager
import android.os.Looper
import androidx.annotation.RequiresPermission


/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object Utils {
    /**
     * 判断是否在主线程
     */
    fun isOnMainThread(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }

    /**
     * 判断WiFi是否可用
     */
    fun isWiFiAvailable(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        val manager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return false
        val networkInfo =
            manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                ?: return false // 得到与WiFi相关的网络信息
        return networkInfo.isAvailable // 判断网络是否可用
    }
}