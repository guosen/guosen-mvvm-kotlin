//
// Created by Guosen Lin on 2021/2/24.
//

#ifndef GUOSEN_MVVM_KOTLIN_CRASHDEFINE_H
#define GUOSEN_MVVM_KOTLIN_CRASHDEFINE_H


#include<android/log.h>
# define TAG "JNI_TAG"
#include <signal.h>
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG , TAG, __VA_ARGS__)

//信号量
const int exceptionSignals[]={SIGSEGV,SIGABRT,SIGFPE,SIGILL,SIGBUS,SIGTRAP};

const int exceptionSigNumber = sizeof(exceptionSignals)/sizeof(exceptionSignals[0]);

static  struct  sigaction oldHandlers[NSIG];

#endif //GUOSEN_MVVM_KOTLIN_CRASHDEFINE_H
