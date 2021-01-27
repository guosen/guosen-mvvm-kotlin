package com.guosen.common.imageloader

import androidx.annotation.IntDef




/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@IntDef(
    ImageType.BITMAP,
    ImageType.DRAWABLE,
    ImageType.GIF,
    ImageType.FILE
)
@Retention(AnnotationRetention.SOURCE)
annotation class ImageType {
    companion object {
        /**
         * Bitmap
         */
        const val  BITMAP = 0

        /**
         * Drawable
         */
        const val DRAWABLE = 1

        /**
         * Gif
         */
        const val GIF = 2

        /**
         * File
         */
        const val FILE = 3
    }
}