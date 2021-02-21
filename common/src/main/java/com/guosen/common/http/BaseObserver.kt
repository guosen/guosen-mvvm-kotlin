package com.guosen.common.http

import android.net.ParseException
import com.google.gson.JsonParseException
import com.guosen.common.bean.BaseBean
import com.guosen.common.util.showToast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/02/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class BaseObserver<T : BaseBean> : Observer<T> {
    override fun onNext(value: T) {
        // 这里面还可以加入额外的一些code判断，因为我这没返回code，所以就不加了
        // gank接口返回的是error，所以这里用error来演示
        value?.let {
            if (0 == it.errCode) {
                onSuccess(it)
            } else {
                showToast("接口返回错误")
                onFailed()
            }
        }
        value ?: showToast("无数据")
    }

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onError(e: Throwable) {
        onErrorAble(e)
    }

    // 成功的处理就交给model层了
    open fun onSuccess(value: T) {}

    // 失败的回调之所以交给model层，是因为需要通过model层的listener回调给presenter层，hideProgress
    open fun onFailed() {}

    // 捕捉异常信息，并吐司提示
    fun onErrorAble(e: Throwable?) {
        when (e) {
            is NullPointerException -> showToast("接口挂了")
            is HttpException -> showToast("Http错误")
            is ConnectException -> showToast("连接错误")
            is UnknownHostException -> showToast("找不到主机")
            is InterruptedException -> showToast("连接超时")
            is SocketTimeoutException -> showToast("请求超时")
            is JsonParseException, is JSONException, is ParseException -> showToast("解析错误")
            else -> showToast("未知错误")
        }
        onFailed()
    }
}