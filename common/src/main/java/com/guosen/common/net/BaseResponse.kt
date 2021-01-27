package com.guosen.common.net

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
data class BaseResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)