package com.guosen.common.net

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> BaseResponse<T>): BaseResponse<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> BaseResponse<T>, errorMessage: String): T? {

        val result:Result<T> = safeApiResult(call,errorMessage)
        var data:T? = null
        when(result){

            is Result.Success ->
                data = result.data
            is Result.Error ->
                Log.d("ss","")
        }

           return data

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

}