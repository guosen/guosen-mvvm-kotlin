package com.guosen.common.imageloader.glide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestFutureTarget
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.signature.ObjectKey
import com.guosen.common.imageloader.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class GlideImageLoaderStrategy(context: Context) :ImageLoaderStrategy {
    private var mContext: Context? = context.applicationContext

    override fun loadFromResource(
        context: Context?,
        imageView: ImageView?,
        resourceId: Int?,
        options: ImageOptions?
    ) {
         load(context,imageView,resourceId,options)
    }

    override fun loadFromAssets(
        context: Context?,
        imageView: ImageView?,
        assetName: String?,
        options: ImageOptions?
    ) {
        load(context, imageView, "file:///android_asset/" + assetName, options);
    }

    override fun loadFromFile(
        context: Context?,
        imageView: ImageView?,
        file: File?,
        options: ImageOptions?
    ) {
        load(context, imageView, file, options)
    }

    override fun loadFromUri(
        context: Context?,
        imageView: ImageView?,
        uri: Uri?,
        options: ImageOptions?
    ) {
        load(context, imageView, uri, options);
    }

    override fun loadFromUrl(
        context: Context?,
        imageView: ImageView?,
        url: String?,
        options: ImageOptions?
    ) {
        load(context, imageView, url, options);

    }

    override fun downloadImage(context: Context?, url: String?, options: ImageOptions?) {
        download(context!!, url, options);
    }

    override fun resumeRequests() {

        mContext?.let { Glide.with(it).resumeRequests() };
    }

    override fun pauseRequests() {
        mContext?.let { Glide.with(it).pauseRequests() };
    }

    override fun cancleRequests(`object`: Any?) {
//        if (`object` is View) {
//            Glide.with(mContext!!).clear(`object` as View?)
//        } else if (`object` is Target) {
//            Glide.with(mContext!!).clear(`object` as Target<*>?)
//        }
    }

    override fun onLowMemory() {
        if (!Utils.isOnMainThread()) {
            throw   IllegalArgumentException("You must call this method on the main thread.");
        }
        mContext?.let { Glide.get(it).onLowMemory() };
    }

    override fun onTrimMemory(level: Int) {
        if (!Utils.isOnMainThread()) {
            throw   IllegalArgumentException("You must call this method on the main thread.");
        }
        mContext?.let { Glide.get(it).onTrimMemory(level) };
    }

    override fun clearMemoryCache() {
        if (!Utils.isOnMainThread()) {
            throw   IllegalArgumentException("You must call this method on the main thread.");
        }
        mContext?.let { Glide.get(it).clearMemory() };
    }

    override fun clearDiskCache() {
        if (!Utils.isOnMainThread()) {
            throw   IllegalArgumentException("You must call this method on the main thread.");
        }
        mContext?.let { Glide.get(it).clearDiskCache() };
    }

    private fun <ModelType : Any> load(
        context: Context?,
        imageView: ImageView?,
        model: ModelType?,
        options: ImageOptions?
    ) {
        requireNotNull(model) { "model is null." }
        val requestManager = Glide.with(context!!)
        if (options != null) {
            val imageType = options.getImageType()
            if (imageType == ImageType.BITMAP) {
                load(requestManager.asBitmap(), imageView!!, model, options)
            } else if (imageType == ImageType.DRAWABLE) {
                load(requestManager.asDrawable(), imageView!!, model, options)
            } else if (imageType == ImageType.GIF) {
                load(requestManager.asGif(), imageView!!, model, options)
            } else if (imageType == ImageType.FILE) {
                load(requestManager.asFile(), imageView!!, model, options)
            } else {
                load(requestManager.asDrawable(), imageView!!, model, options)
            }
        } else {
            load(requestManager.asDrawable(), imageView!!, model, null)
        }
    }

    private fun <ModelType, ResultType> load(
        requestBuilder: RequestBuilder<ResultType>,
        imageView: ImageView,
        model: ModelType,
        options: ImageOptions?
    ) {
        val loadOnlyWifi = options != null && options.isLoadOnlyWifi()
        val loadThumbnail = options != null && options.getThumbnail() > 0
        val listener =
            options?.getRequestListener()
        requestBuilder.apply(generateRequestOptions(model, options))
            .onlyRetrieveFromCache(loadOnlyWifi && !Utils.isWiFiAvailable(mContext))
            .thumbnail(if (loadThumbnail) options!!.getThumbnail() else 1.0f)
            .load(model)
            .addListener(GlideRequestListener<ResultType>(listener))
            .into(object : ImageViewTarget<ResultType?>(imageView) {
                override fun onLoadStarted(@Nullable placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    listener?.onLoadingStarted(getView())
                }

                override fun setResource(@Nullable resource: ResultType?) {
                    if (resource == null) {
                        getView().setImageDrawable(null)
                        return
                    }
                    if (resource is Bitmap) {
                        getView().setImageBitmap(resource as Bitmap?)
                    } else if (resource is Drawable) {
                        getView().setImageDrawable(resource as Drawable?)
                    } else if (resource is File) {
                        try {
                            getView().setImageBitmap(
                                BitmapFactory.decodeStream(
                                    FileInputStream(
                                        resource as File?
                                    )
                                )
                            )
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                    }
                }
            })
    }


    /**
     * 初始化图片加载配置选项
     *
     * @param <ModelType> 数据模型泛型
     * @param model       数据模型
     * @param options     图片加载配置选项
    </ModelType> */
    @SuppressLint("CheckResult")
    private fun <ModelType> generateRequestOptions(
        model: ModelType?,
        options: ImageOptions?
    ): RequestOptions {
        val requestOptions = RequestOptions()
        if (options == null) {
            return requestOptions
        }
        if (options.getPlaceHolderResId() != null && options.getPlaceHolderResId() != -1) {
            requestOptions.placeholder(options.getPlaceHolderResId()!!) // 设置加载中的占位图
        }
        if (options.getErrorResId() != null && options.getErrorResId() != -1) {
            requestOptions.error(options.getErrorResId()!!) // 设置加载失败的占位图
        }
        val imageSize = options.getImageSize()
        if (imageSize != null && imageSize.isValidity()) {
            requestOptions.override(imageSize.getWidth(), imageSize.getHeight()) // 设置加载的图片大小
        } else {
            requestOptions.override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
        }
        if (options.getTag() != null) {
            requestOptions.signature(ObjectKey(options.getTag().toString())) // 设置图片标识
        } else if (model != null && !TextUtils.isEmpty(model.toString())) {
            requestOptions.signature(ObjectKey(model)) // 设置图片标识
        }
        if (options.getPriority() != null) {
            requestOptions.priority(options.getPriority()!!) // 设置图片加载优先级(优先级高的先加载，优先级低的后加载)
        }
        if (options.isSkipMemoryCache()) {
            requestOptions.skipMemoryCache(options.isSkipMemoryCache()) // 设置是否跳过内存缓存
        }
        if (options.getDiskCacheStrategy() != null) {
            requestOptions.diskCacheStrategy(options.getDiskCacheStrategy()!!) // 设置磁盘缓存策略
        }
        if (options.getCropType() == CropType.CENTER_INSIDE) {
            requestOptions.centerInside()
        } else if (options.getCropType() == CropType.CENTER_CROP) {
            requestOptions.centerCrop()
        } else if (options.getCropType() == CropType.FIT_CENTER) {
            requestOptions.fitCenter()
        }
        if (options.getTransformations() != null) {
            requestOptions.transform(*options.getTransformations()!!) // 设置动态转换
        }
        return requestOptions
    }
    private fun download(
        context: Context,
        url: String?,
        options: ImageOptions?
    ) {
        require(!(url == null || url.length == 0)) { "model is null." }
        val listener =
            options?.getRequestListener()
        Glide.with(context)
            .applyDefaultRequestOptions(generateRequestOptions(url, options))
            .download(url)
            .addListener(GlideRequestListener(listener))
            .into(object :
                RequestFutureTarget<File?>(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL) {
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    listener?.onLoadingStarted(null)
                }
            })
    }


    private class GlideRequestListener<T> internal constructor(private val listener: ImageLoadingListener?) :
        RequestListener<T> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<T>?,
            isFirstResource: Boolean
        ): Boolean {
            listener?.onLoadFailed(null, e)
            return false
        }


        override fun onResourceReady(
            resource: T,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<T>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            if (listener != null) {
                listener.onLoadComplete(null, resource);
            }
            return false;

        }

    }
}