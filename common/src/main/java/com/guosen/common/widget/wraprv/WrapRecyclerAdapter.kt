package com.guosen.common.widget.wraprv

import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.core.util.containsKey
import androidx.core.util.containsValue
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
class WrapRecyclerAdapter : RecyclerView.Adapter<BaseViewHolder> {

    //数据的适配器
    private lateinit var mAdapter: RecyclerView.Adapter<BaseViewHolder>

    //头部和底部的集合(map key是int )
    private var heads: SparseArray<View>
    private var foots: SparseArray<View>
    private var BASE_HEADER_KEY = 10000000
    private var BASE_FOOT_KEY = 1000000

    //方法添加头部 移除
    constructor(adapter: RecyclerView.Adapter<BaseViewHolder>) {
        this.mAdapter = adapter
        heads = SparseArray<View>()
        foots = SparseArray<View>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        Log.d("ss","onCreateViewHolder")
        if (heads.containsKey(viewType)) {
            return createHeaderAndFooterViewHolder(heads.get(viewType))
        } else if (foots.indexOfKey(viewType) >= 0) {
            return createHeaderAndFooterViewHolder(foots.get(viewType))
        }
        return mAdapter.onCreateViewHolder(parent, viewType)

    }

    private fun createHeaderAndFooterViewHolder(view: View?): BaseViewHolder {
        Log.d("ss","createHeaderAndFooterViewHolder")
        return BaseViewHolder(view!!)

    }

    override fun getItemCount(): Int {
        Log.d("ss","getItemCount")
        return mAdapter.itemCount + heads?.size() + foots?.size()
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("ss","getItemViewType")
        //positon->type
        //1.头部
        var numHeads = heads.size()
        if (position < numHeads) {
            return heads.keyAt(position)
        }

        //2。中间

        var adPositiopn = position - numHeads
        var adapterItemCount = mAdapter?.itemCount
        if (adPositiopn < adapterItemCount) {
            return mAdapter.getItemViewType(adPositiopn)
        }

        //尾部

        return foots.keyAt(adPositiopn - adapterItemCount)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        Log.d("ss","onBindViewHolder")
        var numHeads = heads.size()
        var adPositiopn = position - numHeads
        if (numHeads - 1 >= adPositiopn) return
        var adapterItemCount = mAdapter?.itemCount
        if (adPositiopn > numHeads + adapterItemCount) return
        mAdapter.onBindViewHolder(holder, adPositiopn)

    }

    fun addHeader(view: View) {
        if (!heads?.containsValue(view)) {
            heads?.put(BASE_HEADER_KEY++, view)
        }
        notifyDataSetChanged()
    }

    fun addFoot(view: View) {
        if (!foots?.containsValue(view)) {
            foots?.put(BASE_FOOT_KEY++, view)
        }
        notifyDataSetChanged()
    }


    fun removeHeaderView(view: View) {
        if (heads?.indexOfValue(view) != -1) {
            heads?.removeAt(heads?.indexOfValue(view)!!)
        }
        notifyDataSetChanged()
    }

    fun removeFootView(view: View) {
        if (foots?.indexOfValue(view) != -1) {
            foots?.removeAt(foots?.indexOfValue(view)!!)
        }
        notifyDataSetChanged()
    }

    fun getDataAdapter():RecyclerView.Adapter<BaseViewHolder>{
        return mAdapter
    }
}