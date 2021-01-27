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
class ImageSize {
    private var width = 0
    private var height = 0

    fun ImageSize(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    fun getWidth(): Int {
        return width
    }

    fun getHeight(): Int {
        return height
    }

    fun isValidity(): Boolean {
        return width > 0 && height > 0
    }
}