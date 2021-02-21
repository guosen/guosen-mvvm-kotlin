package com.guosen.common.http


import android.util.Log
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/02/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class OkHttpUtil private constructor() {
    private var okHttpClient: OkHttpClient? = null
    // 自定义TAG的Log
    private val okHttpLog: OkHttpLog by lazy { OkHttpLog() }
    // 连接超时时间
    private val connectionTime: Long = 10L
    // 写入超时时间
    private val writeTime: Long = 10L
    // 读取超时时间
    private val readTime: Long = 30L
    // 缓存文件
    private val cacheFile: File = File("MyApplication.getInstance().cacheDir.absolutePath")
    // 缓存文件大小
    private val maxSize: Long = 8 * 1024 * 1024
    // OkHttpCache
    private var cache: Cache

    companion object {
        val mInstance: OkHttpUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OkHttpUtil()
        }
    }

    init {
        // 初始化缓存文件
        if (!cacheFile.exists()) cacheFile.mkdir()
        cache = Cache(cacheFile, maxSize)
    }

    // 配置OkHttp
    fun getHttpClient(): OkHttpClient {
        if (null == okHttpClient) {
            okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(connectionTime, TimeUnit.SECONDS)
                    .readTimeout(readTime, TimeUnit.SECONDS)
                    .writeTimeout(writeTime, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor(okHttpLog))
                    .addInterceptor(HeadIntercept())
                    .cache(cache)
                    .build()
        }
        return okHttpClient!!
    }

    // 自定义OkHttpLog
    class OkHttpLog : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("taonce", message)
        }
    }

    // 可以对请求头参数作统一处理，通过下面的`addHeader()`方法
    class HeadIntercept : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain?.let {
                it.proceed(it.request()
                        .newBuilder()
                        .addHeader("key", "key")
                        .build())
            }
        }
    }
}