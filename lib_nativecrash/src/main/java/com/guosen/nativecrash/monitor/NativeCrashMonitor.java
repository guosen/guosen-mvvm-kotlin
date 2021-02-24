package com.guosen.nativecrash.monitor;

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/02/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class NativeCrashMonitor {


    static {
        System.loadLibrary("native-lib");
    }
    private static volatile  boolean isInit = false;
    public void init(CrashHandlerListener callBack){
        if (isInit){
            return;
        }
        isInit = true;
        nativeInit(callBack);
        nativeSetup();

    }


    private native void nativeInit(CrashHandlerListener callBack);

    private native void nativeSetup();

    public native static final void nativeCrash();

}
