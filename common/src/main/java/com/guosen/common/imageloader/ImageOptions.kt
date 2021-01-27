package com.guosen.common.imageloader

import androidx.annotation.DrawableRes
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ImageOptions {
    private val RESOURCE_NONE = -1 // 没有占位图

    private var tag // 唯一标识
            : String? = null
    private var target: Target? = null
    private var placeHolderResId // 默认占位资源
            : Int? = null
    private var errorResId // 错误时显示的资源
            : Int? = null
    private var loadOnlyWifi = false
    private var imageSize // 图片最终显示在ImageView上的宽高度像素
            : ImageSize? = null
    private var cropType // 裁剪类型(默认为中部裁剪)
            = 0
    private var imageType //图片类型
            = 0
    private var priority: Priority? = null
    private var skipMemoryCache // 是否跳过内存缓存(默认false不跳过)
            = false
    private var diskCacheStrategy // 硬盘缓存(默认为all类型)
            : DiskCacheStrategy? = null
    private var thumbnail // 设置缩略图的缩放比例0.0f-1.0f,如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示
            = 0f
    private var animResId // 图片加载完后的动画效果,在异步加载资源完成时会执行该动画。
            : Int? = null
    private  var transformations: Array<BitmapTransformation> = arrayOf()
    private var requestListener: ImageLoadingListener? = null

    fun getDefaultOptions(): ImageOptions? {
        return getDefaultOptions(RESOURCE_NONE, RESOURCE_NONE)
    }

    fun getDefaultOptions(@DrawableRes defaultResId: Int): ImageOptions? {
        return getDefaultOptions(defaultResId, defaultResId)
    }

    fun getDefaultOptions(
        @DrawableRes errorResId: Int,
        @DrawableRes placeHolderResId: Int
    ): ImageOptions? {
        return getDefaultOptions(errorResId, placeHolderResId, CropType.CENTER_CROP)
    }

    fun getDefaultOptions(
        @DrawableRes errorResId: Int,
        @DrawableRes placeHolderResId: Int,
        @CropType cropType: Int
    ): ImageOptions? {
        return Builder()
            .setErrorResId(errorResId)
            .setPlaceHolderResId(placeHolderResId)
            .setImageType(ImageType.BITMAP)
            .setImageSize(null)
            .setSkipMemoryCache(false)
            .setDiskCacheStrategy(DiskCacheStrategy.DATA)
            .setCropType(cropType)
            .build()
    }

    fun newBuilder(options: ImageOptions): Builder? {
        val builder = Builder()
        builder.tag = options.tag
        builder.target = options.target
        builder.placeHolderResId = options.placeHolderResId!!
        builder.errorResId = options.errorResId!!
        builder.imageSize = options.imageSize
        builder.cropType = options.cropType
        builder.imageType = options.imageType
        builder.priority = options.priority
        builder.skipMemoryCache = options.skipMemoryCache
        builder.diskCacheStrategy = options.diskCacheStrategy
        builder.thumbnail = options.thumbnail
        builder.animResId = options.animResId
        builder.transformations = options.transformations
        builder.requestListener = options.requestListener
        return builder
    }

    private fun ImageOptions(builder: Builder) {
        tag = builder.tag
        target = builder.target
        placeHolderResId = builder.placeHolderResId
        errorResId = builder.errorResId
        loadOnlyWifi = builder.loadOnlyWifi
        imageSize = builder.imageSize
        cropType = builder.cropType
        imageType = builder.imageType
        priority = builder.priority
        skipMemoryCache = builder.skipMemoryCache
        diskCacheStrategy = builder.diskCacheStrategy
        thumbnail = builder.thumbnail
        animResId = builder.animResId
        transformations = builder.transformations!!
        requestListener = builder.requestListener
    }

    fun getTag(): String? {
        return tag
    }

    fun getPlaceHolderResId(): Int? {
        return placeHolderResId
    }

    fun getErrorResId(): Int? {
        return errorResId
    }

    fun getImageSize(): ImageSize? {
        return imageSize
    }

    fun getCropType(): Int {
        return cropType
    }

    fun getImageType(): Int {
        return imageType
    }

    fun getPriority(): Priority? {
        return priority
    }

    fun isSkipMemoryCache(): Boolean {
        return skipMemoryCache
    }

    fun isLoadOnlyWifi(): Boolean {
        return loadOnlyWifi
    }

    fun getDiskCacheStrategy(): DiskCacheStrategy? {
        return diskCacheStrategy
    }

    fun getThumbnail(): Float {
        return thumbnail
    }

    fun getAnimResId(): Int? {
        return animResId
    }

    fun getTransformations(): Array<BitmapTransformation>? {
        return transformations
    }

    fun getRequestListener(): ImageLoadingListener? {
        return requestListener
    }

    /**
     * Builder类
     */
    class Builder {
        public var tag //唯一标识
                : String? = null
        public var target: Target? = null
        public var placeHolderResId //默认占位资源
                = 0
        public var errorResId //错误时显示的资源
                = 0
        public var loadOnlyWifi = false
        public var imageSize //图片最终显示在ImageView上的宽高度像素
                : ImageSize? = null
        public var cropType // 裁剪类型,默认为中部裁剪
                = 0
        public var imageType //true,强制显示的是gif动画,如果url不是gif的资源,那么会回调error()
                = 0
        public var priority: Priority? = null
        public var skipMemoryCache //是否跳过内存缓存,默认false不跳过
                = false
        public var diskCacheStrategy //硬盘缓存,默认为all类型
                : DiskCacheStrategy? = null
        public var thumbnail //设置缩略图的缩放比例0.0f-1.0f,如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示
                = 0f
        public var animResId //图片加载完后的动画效果,在异步加载资源完成时会执行该动画。
                : Int? = null
        public  var transformations: Array<BitmapTransformation> = arrayOf()
        public var requestListener: ImageLoadingListener? = null
        fun setTag(tag: String?): Builder {
            this.tag = tag
            return this
        }

        fun setTarget(target: Target?): Builder {
            this.target = target
            return this
        }

        fun setPlaceHolderResId(@DrawableRes placeHolderResId: Int): Builder {
            this.placeHolderResId = placeHolderResId
            return this
        }

        fun setErrorResId(@DrawableRes errorResId: Int): Builder {
            this.errorResId = errorResId
            return this
        }

        fun setLoadOnlyWifi(loadOnlyWifi: Boolean): Builder {
            this.loadOnlyWifi = loadOnlyWifi
            return this
        }

        fun setImageSize(imageSize: ImageSize?): Builder {
            this.imageSize = imageSize
            return this
        }

        fun setCropType( cropType: Int): Builder {
            this.cropType = cropType
            return this
        }

        fun setImageType(imageType: Int): Builder {
            this.imageType = imageType
            return this
        }

        fun setPriority(priority: Priority?): Builder {
            this.priority = priority
            return this
        }

        fun setSkipMemoryCache(skipMemoryCache: Boolean): Builder {
            this.skipMemoryCache = skipMemoryCache
            return this
        }

        fun setDiskCacheStrategy(diskCacheStrategy: DiskCacheStrategy?): Builder {
            this.diskCacheStrategy = diskCacheStrategy
            return this
        }

        fun setThumbnail(thumbnail: Float): Builder {
            this.thumbnail = thumbnail
            return this
        }

        fun setAnimResId(animResId: Int?): Builder {
            this.animResId = animResId
            return this
        }

        fun setTransformations(transformations: Array<BitmapTransformation>): Builder {
            this.transformations = transformations
            return this
        }

        fun setImageLoadingListener(requestListener: ImageLoadingListener?): Builder {
            this.requestListener = requestListener
            return this
        }

        fun build(): ImageOptions {
            return ImageOptions()
        }
    }

}