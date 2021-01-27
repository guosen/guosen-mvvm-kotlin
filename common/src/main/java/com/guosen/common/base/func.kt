package com.guosen.common.base

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.Toast

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 * description:扩展函数
 * 参考url：https://blog.csdn.net/comwill/article/details/77206508
 * 三个特点：
 * 1.Kotlin的扩展函数功能使得我们可以为现有的类添加新的函数，实现某一具体功能 。
 * 2.扩展函数是静态解析的，并未对原类添加函数或属性，对类本身没有任何影响。
 * 3.扩展属性允许定义在类或者kotlin文件中，不允许定义在函数中。
 *     version: 1.0
 * </pre>
 */

//fun Context.getMainComponent() = App.instance.apiComponent

fun Context.toast(msg:String,length:Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, msg, length).show()
}



fun Any.toString():String{
    if(this == null)
        return "null"
    else{
        return toString()
    }
}


// 使用扩展函数
fun View.dp_f(dp: Float): Float {
    // 引用View的context
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
}

// 转换Int
fun View.dp_i(dp: Float): Int {
    return dp_f(dp).toInt()
}