//
// Created by Guosen Lin on 2021/2/24.
//

#ifndef GUOSEN_MVVM_KOTLIN_SIGNALHANDLER_H
#define GUOSEN_MVVM_KOTLIN_SIGNALHANDLER_H

#include "CrashDefine.h"
#include <signal.h>

extern bool installSignalHandlers();

extern void installSpaceStack();

#endif //GUOSEN_MVVM_KOTLIN_SIGNALHANDLER_H
