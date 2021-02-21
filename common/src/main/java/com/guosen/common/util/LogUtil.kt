package com.guosen.common.util

import android.util.Log

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/02/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object LogUtil {
    fun showLog(tag: String = "theOne", msg: String) {
        Log.d(tag, msg)
    }
}