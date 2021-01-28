package com.guosen.common.web

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.guosen.common.R
import com.tencent.smtt.sdk.ValueCallback

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Route(path = "/common/web")
class WebActivity: AppCompatActivity() {

    private var timeLast:Long=0;
    private var webView:GWebView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        webView?.webViewClient = GWebViewClient(webView!!,this)
        webView?.webChromeClient = GWebChromeClient()
        webView?.loadUrl("file:///android_asset/test.html")
    }

    /**
     * 加载js回调
     */
    private fun loadJs(callback: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView?.evaluateJavascript(String.format("javascript:%s", callback), ValueCallback<String> { value: String? ->  })
        } else {
            webView?.loadUrl(String.format("javascript:%s", callback))
        }
    }

    override fun onResume() {
        super.onResume()
        timeLast = System.currentTimeMillis()
    }
}