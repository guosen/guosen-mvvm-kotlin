package com.guosen.common.imageloader

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import java.io.File

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface ImageLoaderStrategy {
    /**
     * 加载资源图片
     *
     * @param context    上下文
     * @param imageView  ImageView对象
     * @param resourceId 图片资源ID
     * @param options    图片加载配置选项
     */
    fun loadFromResource(
        context: Context?,
        imageView: ImageView?,
        resourceId: Int?,
        options: ImageOptions?
    )

    /**
     * 加载Assets图片
     *
     * @param context   上下文
     * @param imageView ImageView对象
     * @param assetName Assets图片名称
     * @param options   图片加载配置选项
     */
    fun loadFromAssets(
        context: Context?,
        imageView: ImageView?,
        assetName: String?,
        options: ImageOptions?
    )

    /**
     * 加载本地图片
     *
     * @param context   上下文
     * @param imageView ImageView对象
     * @param file      本地图片文件
     * @param options   图片加载配置选项
     */
    fun loadFromFile(
        context: Context?,
        imageView: ImageView?,
        file: File?,
        options: ImageOptions?
    )

    /**
     * 加载指定URI的图片
     *
     * @param context   上下文
     * @param imageView ImageView对象
     * @param uri       图片Uri
     * @param options   图片加载配置选项
     */
    fun loadFromUri(
        context: Context?,
        imageView: ImageView?,
        uri: Uri?,
        options: ImageOptions?
    )

    /**
     * 加载网络图片
     *
     * @param context   上下文
     * @param imageView ImageView对象
     * @param url       图片地址Url
     * @param options   图片加载配置选项
     */
    fun loadFromUrl(
        context: Context?,
        imageView: ImageView?,
        url: String?,
        options: ImageOptions?
    )

    /**
     * 下载图片
     *
     * @param context 上下文
     * @param url     图片地址Url
     * @param options 图片加载配置选项
     */
    fun downloadImage(
        context: Context?,
        url: String?,
        options: ImageOptions?
    )

    /**
     * 恢复请求
     */
    fun resumeRequests()

    /**
     * 暂停请求
     */
    fun pauseRequests()

    /**
     * 取消请求
     */
    fun cancleRequests(`object`: Any?)

    /**
     * 在系统内存不足，所有后台程序都被杀死时，系统会调用该方法
     */
    fun onLowMemory()

    /**
     * 系统会根据不同的内存状态，响应不同的内存释放策略
     */
    fun onTrimMemory(level: Int)

    /**
     * 清除内存缓存
     */
    fun clearMemoryCache()

    /**
     * 清除磁盘缓存
     */
    fun clearDiskCache()

}