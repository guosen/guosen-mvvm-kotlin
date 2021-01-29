package com.guosen.common.widget.wraprv.refresh

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable
import androidx.core.view.ViewCompat
import com.guosen.common.widget.wraprv.WrapRecyclerView


/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/29
 *     desc   : https://www.jianshu.com/p/de511e4e23c6
 *     version: 1.0
 * </pre>
 */
class RefreshRecyclerView : WrapRecyclerView {
    // 下拉刷新的辅助类
    private var mRefreshCreator: RefreshViewCreator? = null

    // 下拉刷新头部的高度
    private var mRefreshViewHeight = 0

    // 下拉刷新的头部View
    private var mRefreshView: View? = null

    // 手指按下的Y位置
    private var mFingerDownY = 0

    // 手指拖拽的阻力指数
    private val mDragIndex = 0.35f

    // 当前是否正在拖动
    private var mCurrentDrag = false

    // 当前的状态
    private var mCurrentRefreshStatus = 0

    // 默认状态
    var REFRESH_STATUS_NORMAL = 0x0011

    // 下拉刷新状态
    var REFRESH_STATUS_PULL_DOWN_REFRESH = 0x0022

    // 松开刷新状态
    var REFRESH_STATUS_LOOSEN_REFRESHING = 0x0033

    // 正在刷新状态
    var REFRESH_STATUS_REFRESHING = 0x0033

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {}


    // 先处理下拉刷新，同时考虑刷新列表的不同风格样式，确保这个项目还是下一个项目都能用
    // 所以我们不能直接添加View，需要利用辅助类
    fun addRefreshViewCreator(refreshCreator: RefreshViewCreator?) {
        mRefreshCreator = refreshCreator
        addRefreshView()
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        addRefreshView()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN ->                 // 记录手指按下的位置 ,之所以写在dispatchTouchEvent那是因为如果我们处理了条目点击事件，
                // 那么就不会进入onTouchEvent里面，所以只能在这里获取
                mFingerDownY = ev.rawY.toInt()
            MotionEvent.ACTION_UP -> if (mCurrentDrag) {
                restoreRefreshView()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 重置当前刷新状态状态
     */
    private fun restoreRefreshView() {
        val currentTopMargin = (mRefreshView?.getLayoutParams() as MarginLayoutParams).topMargin
        var finalTopMargin = -mRefreshViewHeight + 1
        if (mCurrentRefreshStatus == REFRESH_STATUS_LOOSEN_REFRESHING) {
            finalTopMargin = 0
            mCurrentRefreshStatus = REFRESH_STATUS_REFRESHING
            if (mRefreshCreator != null) {
                mRefreshCreator!!.onRefreshing()
            }
            if (mListener != null) {
                mListener!!.onRefresh()
            }
        }
        val distance = currentTopMargin - finalTopMargin

        // 回弹到指定位置
        val animator =
            ObjectAnimator.ofFloat(currentTopMargin.toFloat(), finalTopMargin.toFloat())
                .setDuration(distance.toLong())
        animator.addUpdateListener { animation ->
            val currentTopMargin = animation.animatedValue as Float
            setRefreshViewMarginTop(currentTopMargin.toInt())
        }
        animator.start()
        mCurrentDrag = false
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                // 如果是在最顶部才处理，否则不需要处理
                if (canScrollUp() || mCurrentRefreshStatus == REFRESH_STATUS_REFRESHING) {
                    // 如果没有到达最顶端，也就是说还可以向上滚动就什么都不处理
                    return super.onTouchEvent(e)
                }

                // 解决下拉刷新自动滚动问题
                if (mCurrentDrag) {
                    scrollToPosition(0)
                }

                // 获取手指触摸拖拽的距离
                val distanceY = ((e.rawY - mFingerDownY) * mDragIndex).toInt()
                // 如果是已经到达头部，并且不断的向下拉，那么不断的改变refreshView的marginTop的值
                if (distanceY > 0) {
                    val marginTop = distanceY - mRefreshViewHeight
                    setRefreshViewMarginTop(marginTop)
                    updateRefreshStatus(marginTop)
                    mCurrentDrag = true
                    return false
                }
            }
        }
        return super.onTouchEvent(e)
    }

    /**
     * 更新刷新的状态
     */
    private fun updateRefreshStatus(marginTop: Int) {
        mCurrentRefreshStatus = if (marginTop <= -mRefreshViewHeight) {
            REFRESH_STATUS_NORMAL
        } else if (marginTop < 0) {
            REFRESH_STATUS_PULL_DOWN_REFRESH
        } else {
            REFRESH_STATUS_LOOSEN_REFRESHING
        }
        if (mRefreshCreator != null) {
            mRefreshCreator!!.onPull(marginTop, mRefreshViewHeight, mCurrentRefreshStatus)
        }
    }

    /**
     * 添加头部的刷新View
     */
    private fun addRefreshView() {
        val adapter  = mAdapter
        if (adapter != null && mRefreshCreator != null) {
            // 添加头部的刷新View
            val refreshView: View? = mRefreshCreator!!.getRefreshView(context, this)
            if (refreshView != null) {
                addHeader(refreshView)
                mRefreshView = refreshView
            }
        }
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        super.onLayout(changed, l, t, r, b)
        if (changed) {
            if (mRefreshView != null && mRefreshViewHeight <= 0) {
                // 获取头部刷新View的高度
                mRefreshViewHeight = mRefreshView!!.getMeasuredHeight()
                if (mRefreshViewHeight > 0) {
                    // 隐藏头部刷新的View  marginTop  多留出1px防止无法判断是不是滚动到头部问题
                    setRefreshViewMarginTop(-mRefreshViewHeight + 1)
                }
            }
        }
    }

    /**
     * 设置刷新View的marginTop
     */
    fun setRefreshViewMarginTop(marginTop: Int) {
        var marginTop = marginTop
        val params = mRefreshView?.getLayoutParams() as MarginLayoutParams
        if (marginTop < -mRefreshViewHeight + 1) {
            marginTop = -mRefreshViewHeight + 1
        }
        params.topMargin = marginTop
        mRefreshView?.setLayoutParams(params)
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 判断是不是滚动到了最顶部，这个是从SwipeRefreshLayout里面copy过来的源代码
     */
    fun canScrollUp(): Boolean {
        return if (Build.VERSION.SDK_INT < 14) {
            ViewCompat.canScrollVertically(this, -1) || this.scrollY > 0
        } else {
            ViewCompat.canScrollVertically(this, -1)
        }
    }

    /**
     * 停止刷新
     */
    fun onStopRefresh() {
        mCurrentRefreshStatus = REFRESH_STATUS_NORMAL
        restoreRefreshView()
        if (mRefreshCreator != null) {
            mRefreshCreator!!.onStopRefresh()
        }
    }

    // 处理刷新回调监听
    private var mListener: OnRefreshListener? = null
    fun setOnRefreshListener(listener: OnRefreshListener?) {
        mListener = listener
    }

    interface OnRefreshListener {
        fun onRefresh()
    }
}