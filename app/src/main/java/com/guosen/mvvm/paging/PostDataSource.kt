package com.guosen.mvvm.paging

import androidx.paging.PagingSource
import com.guosen.mvvm.WanService
import luyao.wanandroid.model.bean.Article
import java.lang.Exception

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/02/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class PostDataSource (private val apiService:WanService) : PagingSource<Int,Article>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        try {
            val currentLoadingPageKey = params.key ?:1
            val response = apiService.getHomeArticles(currentLoadingPageKey)
            val responseData = mutableListOf<Article>()
            val data = response.data.datas?: emptyList()
            responseData.addAll(data)
            val  preKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey-1
            return LoadResult.Page(
                data = responseData,
                prevKey = preKey,
                nextKey = currentLoadingPageKey.plus(1)

            )
        }catch (e:Exception){
            return LoadResult.Error(e)
        }


    }
}