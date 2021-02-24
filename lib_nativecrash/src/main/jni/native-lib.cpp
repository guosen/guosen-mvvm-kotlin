#include <jni.h>
#include <string>
#include<android/log.h>

#include "SignalHandler.h"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG , "ProjectName", __VA_ARGS__)


/**
 * 定义一个结构图
 */
struct JINNativeInterface{

    char *(*NewStringUTF)(JNIEnv*, char*);
};
char* NewStringUTF(JNIEnv* jniEnv,char* str){
    return str;
}

//int main(){
//
//}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_guosen_ndklearn_Mat_n_1Mat__(JNIEnv *env, jclass clazz) {
    // TODO: implement n_Mat()
}extern "C"
JNIEXPORT jlong JNICALL
Java_com_guosen_ndklearn_Mat_n_1Mat__III(JNIEnv *env, jclass clazz, jint size_with, jint size_height,
                                         jint type) {





}extern "C"
JNIEXPORT void JNICALL
Java_com_guosen_nativecrash_monitor_NativeCrashMonitor_nativeInit(JNIEnv *env, jobject thiz,
                                                                  jobject call_back) {

    //主要是把callBack保存下来，方便监听到异常 回调Java



}extern "C"
JNIEXPORT void JNICALL
Java_com_guosen_nativecrash_monitor_NativeCrashMonitor_nativeSetup(JNIEnv *env, jobject thiz) {


    //捕捉信号
    installSignalHandlers();
    //额外的栈空间，让信号量在单独的栈中处理
    installSpaceStack();




}extern "C"
JNIEXPORT void JNICALL
Java_com_guosen_nativecrash_monitor_NativeCrashMonitor_nativeCrash(JNIEnv *env, jclass clazz) {
    int * num = NULL;
    *num=100;


}

