package com.guosen.mvvm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.guosen.common.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MyViewModel : BaseViewModel() {


    private val repository : ArticleRepository = ArticleRepository()
    fun fetchArticles(){

        viewModelScope.launch {
            val articles = repository.getArticleList(1){
                    e, s ->
                Log.d("ss","error".plus(s))
            }?.let {

                 Log.d("ss","success....")

            }

        }

    }
}