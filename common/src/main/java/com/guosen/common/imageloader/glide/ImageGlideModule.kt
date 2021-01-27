package com.guosen.common.imageloader.glide

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions


/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@GlideModule
class ImageGlideModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }


   override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        Log.i(TAG, "AppGlideModule初始化...")

        // 设置日志级别
        builder.setLogLevel(Log.DEBUG)

        // 设置内存缓存和Bitmap池
        val calculator = MemorySizeCalculator.Builder(context).build()
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))

        // 设置磁盘缓存
        builder.setDiskCache(
            ExternalPreferredCacheDiskCacheFactory(
                context,
                DISK_CACHE_NAME,
                DISK_CACHE_SIZE.toLong()
            )
        )

        // 设置默认的请求选项
        builder.setDefaultRequestOptions(
            RequestOptions()
                .centerCrop() // 设置转换类型
                .dontAnimate() // 设置禁用动画
                .format(DecodeFormat.PREFER_ARGB_8888) // 设置图片格式
        )

        // 设置处理未捕获异常的策略
        builder.setSourceExecutor(GlideExecutor.newSourceExecutor())
        builder.setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor())
    }

    companion object {
        private const val TAG = "GlideModelConfig"
        private const val DISK_CACHE_NAME = "images"
        private const val DISK_CACHE_SIZE = 200 * 1024 * 1024 // 200MB
    }
}