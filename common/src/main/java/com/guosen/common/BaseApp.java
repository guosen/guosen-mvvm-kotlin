package com.guosen.common;

import android.app.Application;

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/02/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */

 public class BaseApp extends Application {

     public static BaseApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
