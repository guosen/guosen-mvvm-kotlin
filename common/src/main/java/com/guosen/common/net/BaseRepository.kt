package com.guosen.common.net

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> BaseResponse<T>,errorMessage: String): BaseResponse<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> BaseResponse<T>, errorMessage: (code:Int,e:String?)->Unit): T? {

        try {

            val result  = call.invoke()
            var data: T? = null
            return result.data
        }catch (e:Exception){
            errorHandle(e,errorMessage)
        }
        return null

    }

    suspend fun <T : Any> executeResponse(response: BaseResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): Result<T> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                Result.Error(IOException(response.errorMsg))
            } else {
                successBlock?.let { it() }
                Result.Success(response.data)
            }
        }
    }
    private suspend fun <T: Any> safeApiResult(call: suspend ()-> BaseResponse<T>, errorMessage: String) : Result<T>{
        val response = call.invoke()
        if(response.errorCode==0) return Result.Success(response.data)

        return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }


    private fun errorHandle(e: Exception, customErrorHandle: (code: Int, msg: String?) -> Unit) {
        when (e) {

            is HttpException -> {
                customErrorHandle(e.code(), e.message())
            }
            is UnknownHostException -> {
                defaultError(400, "无法连接到服务器")
            }
            is SocketTimeoutException -> {
                defaultError(400, "链接超时")
            }
            is ConnectException -> {
                defaultError(500, "链接失败")
            }
            is SocketException -> {
                defaultError(500, "链接关闭")
            }
            is EOFException -> {
                defaultError(500, "链接关闭")
            }
            is IllegalArgumentException -> {
                defaultError(400, "参数错误")
            }
            is SSLException -> {
                defaultError(500, "证书错误")
            }
            is NullPointerException -> {
                defaultError(500, "数据为空")
            }
            else -> {
                defaultError(500, "未知错误")
            }
        }
        Log.e("ss", e.toString())

    }

    private val defaultError = fun(_: Int, msg: String?) {
        Log.e("TAG", msg!!)
    }
}