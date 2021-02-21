package com.guosen.common.http

import com.guosen.common.bean.BaseBean

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   : 请求服务端返回
 *     version: 1.0
 * </pre>
 */
data class BaseResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T): BaseBean()