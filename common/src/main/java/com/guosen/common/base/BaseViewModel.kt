package com.guosen.common.base
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*


/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
open class BaseViewModel : ViewModel(){
    //UI状态通知
    open class BaseUiState<T>(
        var showLoading: Boolean = false,
        var showError: String? = null,
        var showSuccess: T? = null,
        var nextPage: Boolean = false, // 加载更多
        var endPage: Boolean = false //

    )

    val mException: MutableLiveData<Throwable> = MutableLiveData()
    //函数作为参数
    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }


    public fun launch(tryBlock:suspend CoroutineScope.() -> Unit){

        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }

    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Throwable) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mException.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }
}