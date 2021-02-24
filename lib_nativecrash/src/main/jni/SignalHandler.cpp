//
// Created by Guosen Lin on 2021/2/24.
//

#include "SignalHandler.h"
#include <android/log.h>
#include <string>
#include <math.h>
#include<unistd.h>

void signalPass(int signum, siginfo_t  *si, void*sc){

    //防止进入死循环
    signal(signum,SIG_DFL);
    signal(SIGALRM,SIG_DFL);
    (void)alarm(8);
    LOGD("监听到了崩溃");
    //给系统默认处理，否则进入死循环
    oldHandlers[signum].sa_sigaction(signum,si,sc);
}

void installSpaceStack(){

    //先把原来的拿出来，可能会有一些其他框架早已经设置好了，bugly,qapm
    //设置过来 不需要设置

    stack_t old_stack;
    stack_t new_stack;
    static const unsigned signalStackSize = std::max(16284,SIGSTKSZ);
//    new_stack.ss_sp = calloc(1,signalStackSize);
    memset(&new_stack,0,sizeof(new_stack));
    memset(&new_stack,0,sizeof(old_stack));

    if (sigaltstack(NULL,&old_stack)==-1 || !old_stack.ss_sp
       || old_stack.ss_size < signalStackSize){
        new_stack.ss_sp = calloc(1,signalStackSize);
        new_stack.ss_size = signalStackSize;
    }

    if (sigaltstack(&new_stack,NULL) == -1){
        free(new_stack.ss_sp);
    }

}
// signum：代表信号编码，可以是除SIGKILL及SIGSTOP外的任何一个特定有效的信号，如果为这两个信号定义自己的处理函数，将导致信号安装错误。
// act：指向结构体sigaction的一个实例的指针，该实例指定了对特定信号的处理，如果设置为空，进程会执行默认处理。
// oldact：和参数act类似，只不过保存的是原来对相应信号的处理，也可设置为NULL。
// int sigaction(int signum, const struct sigaction *act, struct sigaction *oldact));
/**
 *
 * @return
 */
 bool installSignalHandlers(){


     //需要保存原来的处理
    for (int i = 0 ; i < exceptionSigNumber ; i++){
        if (sigaction(exceptionSignals[i],NULL,&oldHandlers[i]) == -1){
            LOGD("有问题");
            return false;
        }
    }


    LOGD("installSignalHandlers");

    struct sigaction sa;
    memset(&sa, 0, sizeof(sa));
    sigemptyset(&sa.sa_mask);
    //不关心其他的,处理当前信号量的时候不关心其他的
    for (int i = 0 ; i < exceptionSigNumber ; i++){
        sigaddset(&sa.sa_mask,exceptionSignals[i]);
    }
    //指定信号处理的回调
    //进入内核-》检查回调-》用户空间
    sa.sa_sigaction = signalPass;
    //1.调用sigaction
    sa.sa_flags = SA_ONSTACK | SA_SIGINFO;
    for (int i = 0 ; i < exceptionSigNumber ; i++){
        if (sigaction(exceptionSignals[i],&sa,NULL) == -1){
            LOGD("有问题");
        }
    }

    return true;


}