package com.guosen.common.util

import android.widget.Toast
import com.guosen.common.BaseApp

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/02/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
fun showToast(msg: String) {
    Toast.makeText(BaseApp.instance?.applicationContext,
            msg, Toast.LENGTH_SHORT).show()
}
