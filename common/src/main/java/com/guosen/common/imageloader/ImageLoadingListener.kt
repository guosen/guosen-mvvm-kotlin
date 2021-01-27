package com.guosen.common.imageloader

import android.widget.ImageView

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
open interface ImageLoadingListener {
    fun onLoadingStarted(imageView: ImageView?)
    fun onLoadFailed(imageView: ImageView?, e: Exception?)
    fun <ResultType> onLoadComplete(imageView: ImageView?, model: ResultType)
}