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

    constructor(context: Context, attributes: AttributeSet?) : super(context, attributes) {

    }

    var mAdapter: WrapRecyclerAdapter? = null
    var observer: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            mAdapter?.notifyDataSetChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mAdapter?.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            mAdapter?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            mAdapter?.notifyItemRangeChanged(fromPosition, toPosition, itemCount)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mAdapter?.notifyItemRangeRemoved(positionStart, itemCount)
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {

        if (adapter is WrapRecyclerAdapter) {
            mAdapter = adapter
        } else {
            mAdapter =
                WrapRecyclerAdapter(adapter as Adapter<BaseViewHolder>)
            mAdapter?.registerAdapterDataObserver(observer)
        }
        super.setAdapter(mAdapter)
    }

    fun addHeader(view: View) {
        mAdapter?.addHeader(view)
    }

    fun addFoot(view: View) {
        mAdapter?.addFoot(view)
    }

    fun removeHeaderView(view: View) {
        mAdapter?.removeHeaderView(view)
    }

    fun removeFootView(view: View) {
        mAdapter?.removeFootView(view)
    }
}