package com.guosen.common.imageloader

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.guosen.common.imageloader.glide.GlideImageLoaderStrategy
import java.io.File


/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   : 策略模式
 *     version: 1.0
 * </pre>
 */
class ImageLoaderHelper private constructor() : BaseImageLoaderHelper {
    private var mDefaultStrategy: ImageLoaderStrategy?=null
    private var mCustomStrategy: ImageLoaderStrategy? = null
    private var sContext: Context? = null


    companion object {
        @Volatile private var instance: ImageLoaderHelper? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ImageLoaderHelper().also { instance = it }
            }
    }

    fun init(context: Context) {
        sContext = context.getApplicationContext()
        requireNotNull(sContext) { "Please invoke init() method first." }
        mDefaultStrategy = GlideImageLoaderStrategy(sContext!!)
        setCustomStrategy(mDefaultStrategy)
    }
    /**
     * 设置默认图片加载策略
     */
    override fun setDefaultStrategy() {
        mCustomStrategy = mDefaultStrategy
    }

    /**
     * 返回当前图片加载策略
     */
    override fun getCustomStrategy(): ImageLoaderStrategy? {
        return mCustomStrategy
    }

    /**
     * 设置自定义图片加载策略
     *
     * @param strategy 图片加载策略
     */
    override fun setCustomStrategy(strategy: ImageLoaderStrategy?) {
        mCustomStrategy = strategy
    }

    override fun loadFromResource(
        context: Context?,
        target: ImageView?,
        resourceId: Int?,
        options: ImageOptions?
    ) {
        mCustomStrategy!!.loadFromResource(context, target, resourceId, options)
    }

    override fun loadFromAssets(
        context: Context?,
        target: ImageView?,
        assetName: String?,
        options: ImageOptions?
    ) {
        mCustomStrategy!!.loadFromAssets(context, target, assetName, options)
    }

    override fun loadFromFile(
        context: Context?,
        target: ImageView?,
        file: File?,
        options: ImageOptions?
    ) {
        mCustomStrategy!!.loadFromFile(context, target, file, options)
    }

    override fun loadFromUri(
        context: Context?,
        imageView: ImageView?,
        uri: Uri?,
        options: ImageOptions?
    ) {
        mCustomStrategy!!.loadFromUri(context, imageView, uri, options)
    }

    override fun loadFromUrl(
        context: Context?,
        target: ImageView?,
        url: String?,
        options: ImageOptions?
    ) {
        mCustomStrategy!!.loadFromUrl(context, target, url, options)
    }

    override fun downloadImage(context: Context?, url: String?, options: ImageOptions?) {
        mCustomStrategy!!.downloadImage(context, url, options)
    }

    override fun resumeRequests() {
        mCustomStrategy!!.resumeRequests()
    }

    override fun pauseRequests() {
        mCustomStrategy!!.pauseRequests()
    }

    override fun cancleRequests(`object`: Any?) {
        mCustomStrategy!!.cancleRequests(`object`)
    }

    override fun onLowMemory() {
        mCustomStrategy!!.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        mCustomStrategy!!.onTrimMemory(level)
    }

    override fun clearMemoryCache() {
        mCustomStrategy!!.clearMemoryCache()
    }

    override fun clearDiskCache() {
        mCustomStrategy!!.clearDiskCache()
    }



}