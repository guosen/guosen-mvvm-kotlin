package com.guosen.common.widget.wraprv.refresh


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.guosen.common.R


/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class DefaultRefreshCreator : RefreshViewCreator() {
    // 加载数据的ImageView
    private var mRefreshIv: View? = null

    override fun getRefreshView(context: Context?, parent: ViewGroup?): View? {
        val refreshView: View =
            LayoutInflater.from(context).inflate(R.layout.layout_refresh_header_view, parent, false)
        mRefreshIv = refreshView.findViewById(R.id.refresh_iv)
        return refreshView
    }

    override fun onPull(
        currentDragHeight: Int,
        refreshViewHeight: Int,
        currentRefreshStatus: Int
    ) {
        val rotate = currentDragHeight.toFloat() / refreshViewHeight
        // 不断下拉的过程中不断的旋转图片
        mRefreshIv?.setRotation(rotate * 360)
    }

    override fun onRefreshing() {
        // 刷新的时候不断旋转
        val animation = RotateAnimation(
            0f, 720f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        animation.repeatCount = -1
        animation.duration = 1000
        mRefreshIv?.startAnimation(animation)
    }

    override fun onStopRefresh() {
        // 停止加载的时候清除动画
        mRefreshIv?.setRotation(0f)
        mRefreshIv?.clearAnimation()
    }
}
