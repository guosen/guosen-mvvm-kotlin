package com.guosen.mvvm

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagingData
import com.alibaba.android.arouter.launcher.ARouter
import com.guosen.common.base.BaseVMActivity
import com.guosen.common.event.EventMessage
import com.guosen.common.imageloader.ImageLoaderHelper
import com.guosen.common.imageloader.ImageOptions
import org.greenrobot.eventbus.EventBus


class MainActivity() : BaseVMActivity<MyViewModel>() {


    override fun initVM(): MyViewModel {
        return MyViewModel()
    }

    override fun initView() {
        var iv = findViewById<ImageView>(R.id.iv)
        ImageLoaderHelper.getInstance().loadFromUrl(this,iv,"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1757702845,51708014&fm=26&gp=0.jpg", ImageOptions())
    }

    override fun initData() {
        mViewModel.fetchArticles()


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun handleEvent(msg: EventMessage) {
        super.handleEvent(msg)
        Log.d("ss",msg.msg)
         PagingData
    }

    fun eventTest(view:View){
        ARouter.getInstance().build("/app/video")
                .navigation()

        EventBus.getDefault().post(EventMessage("11","qq",1,2,null))

    }
}