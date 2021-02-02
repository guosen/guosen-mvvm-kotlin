package com.guosen.common.widget.wraprv

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
open class WrapRecyclerView : RecyclerView {
    // 增加一些通用功能
    // 空列表数据应该显示的空View
    // 正在加载数据页面，也就是正在获取后台接口页面
    private var mEmptyView: View? = null  // 增加一些通用功能

    // 空列表数据应该显示的空View
    // 正在加载数据页面，也就是正在获取后台接口页面
    private var mLoadingView: View? = null
    constructor(context: Context, attributes: AttributeSet?) : super(context, attributes) {

    }

    var mWrapRecyclerAdapter: WrapRecyclerAdapter? = null
    var mAdapter:RecyclerView.Adapter<BaseViewHolder>?=null;
    var observer: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            if (mAdapter == null)return
            if (mAdapter!=mWrapRecyclerAdapter) {
                mWrapRecyclerAdapter?.notifyDataSetChanged()
            }
            dataChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            if (mAdapter == null)return
            if (mAdapter!=mWrapRecyclerAdapter) {
                mWrapRecyclerAdapter?.notifyItemRangeChanged(positionStart, itemCount)
            }
            dataChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (mAdapter == null)return
            if (mAdapter!=mWrapRecyclerAdapter) {
                mWrapRecyclerAdapter?.notifyItemRangeInserted(positionStart, itemCount)
            }
            dataChanged()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            if (mAdapter == null)return
            if (mAdapter!=mWrapRecyclerAdapter) {
                mWrapRecyclerAdapter?.notifyItemRangeChanged(fromPosition, toPosition, itemCount)
            }
            dataChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            if (mAdapter == null)return
            if (mAdapter!=mWrapRecyclerAdapter) {
                mWrapRecyclerAdapter?.notifyItemRangeRemoved(positionStart, itemCount)
            }
            dataChanged()
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (adapter is WrapRecyclerAdapter) {
            mWrapRecyclerAdapter = adapter
            mAdapter = mWrapRecyclerAdapter?.getDataAdapter()
        } else {
            mAdapter= adapter as Adapter<BaseViewHolder>?
            mWrapRecyclerAdapter =
                WrapRecyclerAdapter(adapter as Adapter<BaseViewHolder>)
            mAdapter?.registerAdapterDataObserver(observer)
        }
        super.setAdapter(mWrapRecyclerAdapter)
    }

    fun addHeader(view: View) {
        mWrapRecyclerAdapter?.addHeader(view)
    }

    fun addFoot(view: View) {
        mWrapRecyclerAdapter?.addFoot(view)
    }

    fun removeHeaderView(view: View) {
        mWrapRecyclerAdapter?.removeHeaderView(view)
    }

    fun removeFootView(view: View) {
        mWrapRecyclerAdapter?.removeFootView(view)
    }


    /**
     * 添加一个空列表数据页面
     */
    open fun addEmptyView(emptyView: View) {
        this.mEmptyView = emptyView
    }

    /**
     * 添加一个正在加载数据的页面
     */
    open fun addLoadingView(loadingView: View) {
        this.mLoadingView = loadingView
    }

    /**
     * Adapter数据改变的方法
     */
    private  fun dataChanged() {
        if (mAdapter!!.itemCount == 0) {
            // 没有数据
            if (mEmptyView != null) {
                mEmptyView?.setVisibility(View.VISIBLE)
            } else {
                mEmptyView?.setVisibility(View.GONE)
            }
        }
    }
}