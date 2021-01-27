package com.guosen.common.imageloader

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
open interface BaseImageLoaderHelper : ImageLoaderStrategy {
    /**
     * 设置默认图片加载策略
     */
    fun setDefaultStrategy()

    /**
     * 返回当前图片加载策略
     */
    fun getCustomStrategy(): ImageLoaderStrategy?

    /**
     * 设置自定义图片加载策略
     *
     * @param strategy 图片加载策略
     */
    fun setCustomStrategy(strategy: ImageLoaderStrategy?)
}