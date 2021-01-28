package com.guosen.mvvm

import android.app.Application
import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.guosen.common.imageloader.ImageLoaderHelper
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import kotlin.properties.Delegates


/**
 * Created by luyao
 * on 2018/3/13 13:35
 */
class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
       // lateinit var CURRENT_USER: User
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
       // Timer.start(APP_START)
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        ImageLoaderHelper.getInstance().init(this)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this);
         // 在调用TBS初始化、创建WebView之前进行如下配置
        // 在调用TBS初始化、创建WebView之前进行如下配置
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
        //x5内核初始化接口
//        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
//
//            override fun onViewInitFinished(arg0: Boolean) {
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.d("app", " onViewInitFinished is $arg0")
//            }
//
//            override fun onCoreInitFinished() {
//            }
//        })
    }
}