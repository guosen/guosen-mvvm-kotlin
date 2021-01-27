package com.guosen.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.guosen.common.event.EventCode
import com.guosen.common.event.EventMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity() {


    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
        mViewModel = initVM()
        initView()
        initData()

    }

    abstract fun initVM(): VM
    abstract fun initView()
    abstract fun initData()
    abstract fun getLayoutId():Int

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }


    //事件传递
    @Subscribe
    fun onEventMainThread(msg: EventMessage) {
        handleEvent(msg)
    }


    /**
     * 消息、事件接收回调
     */
    open fun handleEvent(msg: EventMessage) {
        if (msg.code == EventCode.LOG_OUT) {
            //dosomething
        }
    }
}