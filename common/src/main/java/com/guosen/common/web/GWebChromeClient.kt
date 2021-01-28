package com.guosen.common.web

import android.net.Uri
import android.util.Log
import com.tencent.smtt.export.external.interfaces.JsPromptResult
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class GWebChromeClient : WebChromeClient() {


    // 拦截输入框(原理同方式2)
    // 参数message:代表promt（）的内容（不是url）
    // 参数result:代表输入框的返回值
    override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {

        // 根据协议的参数，判断是否是所需要的url(原理同方式2)
        // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
        //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
        var uri:Uri = Uri.parse(message)
        if (uri.scheme=="js"){

            if (uri.authority == "webview"){
                Log.d("ss","android fangfa ")
                // 可以在协议上带有参数并传递到Android上
                //HashMap<String, String> params = new HashMap<>();
                //Set<String> collection = uri.getQueryParameterNames();

                result?.confirm("scuess")
            }
            return true
        }




        return super.onJsPrompt(view, url, message, defaultValue, result)
    }
}