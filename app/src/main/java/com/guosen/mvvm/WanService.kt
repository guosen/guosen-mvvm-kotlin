package com.guosen.mvvm

import com.guosen.common.net.BaseResponse
import luyao.wanandroid.model.bean.ArticleList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by luyao
 * on 2018/3/13 14:33
 */
interface WanService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): BaseResponse<ArticleList>
}