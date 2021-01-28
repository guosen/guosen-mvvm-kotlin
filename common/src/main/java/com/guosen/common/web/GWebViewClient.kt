package com.guosen.common.web

import android.content.Context
import android.util.Log
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class GWebViewClient : WebViewClient {

    private var context:Context?=null
    private var webView:GWebView?=null;
    /**
     * 构造方法
     * @param webView                           需要传进来webview
     * @param context                           上下文
     */
    constructor (webView: GWebView, context: Context) {
        this.context = context
        this.webView = webView
        //将js对象与java对象进行映射
        //webView.addJavascriptInterface(new ImageJavascriptInterface(context), "imagelistener");
    }



    /**
     * 这个方法中可以做拦截
     * 主要的作用是处理各种通知和请求事件
     *
     * 不准确的说法如下：
     * 1.返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
     * 2.返回: return true; 表示webView处理url是根据程序来执行的。 返回: return false; 表示webView处理url是在webView内部执行。
     * 3.还有一种错误说法：WebView上的所有加载都经过这个方法。
     *
     * 准确说法，该方法说明如下所示：
     * 若没有设置 WebViewClient 则由系统（Activity Manager）处理该 url，通常是使用浏览器打开或弹出浏览器选择对话框。
     * 若设置 WebViewClient 且该方法返回 true ，则说明由应用的代码处理该 url，WebView 不处理，也就是程序员自己做处理。
     * 若设置 WebViewClient 且该方法返回 false，则说明由 WebView 处理该 url，即用 WebView 加载该 url。
     * @param view                              view
     * @param url                               链接
     * @return                                  是否自己处理，true表示自己处理
     */
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun onPageFinished(p0: WebView?, p1: String?) {
        super.onPageFinished(p0, p1)
    }


}