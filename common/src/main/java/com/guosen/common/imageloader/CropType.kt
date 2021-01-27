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
/**
 * 裁剪类型
 *
 * @author Henley
 * @since 2017/3/31 9:44
 */
@IntDef(
    CropType.CENTER_CROP,
    CropType.CENTER_INSIDE,
    CropType.FIT_CENTER
)
@Retention(AnnotationRetention.SOURCE)
annotation class CropType {
    companion object {
        /**
         * 中间裁剪
         */
        const val CENTER_CROP = 0

        /**
         * 中间裁剪
         */
        const val CENTER_INSIDE = 1

        /**
         * 适合居中
         */
        const val FIT_CENTER = 2
    }
}