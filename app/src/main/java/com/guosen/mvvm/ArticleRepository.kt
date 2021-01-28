package com.guosen.mvvm

import android.util.Log
import com.guosen.common.net.BaseRepository
import com.guosen.common.net.BaseResponse
import luyao.wanandroid.model.bean.ArticleList

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ArticleRepository : BaseRepository() {

    suspend fun getArticleList(page: Int,err:(e:Int,s:String?)->Unit): ArticleList?{

         val articleResponse = safeApiCall(call = {WanRetrofitClient.service.getHomeArticles(page) } ,errorMessage = err)

         return articleResponse
    }

}