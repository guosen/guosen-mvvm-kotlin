package com.guosen.common.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/02/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
/**
 *
 *
 *                 ubscribeOn(Schedulers.io())
.unsubscribeOn(Schedulers.io())
.observeOn(AndroidSchedulers.mainThread())
.subscribe(new BaseObserver<BaseResponse<ArticleList>>() {
@Override
public void onSuccess(@NotNull BaseResponse<ArticleList> value) {
observerUIState(false);
articleListMutableLiveData.postValue(value.getData());
}
@Override
public void onFailed() {
observerUIState(false);
}

@Override
public void onError(@NotNull Throwable e) {
observerUIState(false);
}
});
 *
 *
 *
 *
 *
 *
 *
 *
 */
 class RetrofitUtil private constructor(){
    private val okHttpClient: OkHttpClient = OkHttpUtil.mInstance.getHttpClient()
    private var retrofit: Retrofit? = null


    companion object {
        private var mInstance: RetrofitUtil? = null
            get() {
                if (field == null) {
                    field = RetrofitUtil()
                }
                return field
            }
         fun getInstance() : RetrofitUtil{
            return mInstance!!
        }
    }

    // 配置Retrofit
    private fun getRetrofit(): Retrofit? {
        if (null == retrofit) {
            retrofit = Retrofit.Builder()
                    .baseUrl("ApiService.BASE_URL")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
        }
        return retrofit
    }

    // 返回BaseService对象，调用其具体接口方法，获取Observable<T>对象
    //fun getService(): ApiService = mInstance?.getRetrofit()!!.create(ApiService::class.java)
}