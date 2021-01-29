package com.guosen.mvvm

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.guosen.common.imageloader.ImageLoaderHelper
import com.guosen.mvvm.VideoAdapter.VideoViewHolder
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController
import org.yczbj.ycvideoplayerlib.player.VideoPlayer

class VideoAdapter(
    private val mContext: Context,
    private val mVideoList: List<Video>?
) : RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(
            R.layout.item_video_pager,
            parent, false
        )
        val holder = VideoViewHolder(itemView)
        //创建视频播放控制器，主要只要创建一次就可以呢
        val controller = VideoPlayerController(mContext)
        holder.setController(controller)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        val video = mVideoList!![position]
        (holder as VideoViewHolder).bindData(video, position)
    }

    override fun getItemCount(): Int {
        return mVideoList?.size ?: 0
    }

    inner class VideoViewHolder  constructor(itemView: View) :
        BaseViewHolder(itemView) {
        var mController: VideoPlayerController? = null
        var mVideoPlayer: VideoPlayer

        /**
         * 设置视频控制器参数
         * @param controller            控制器对象
         */
        fun setController(controller: VideoPlayerController?) {
            mController = controller
            mVideoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_IJK)
            mVideoPlayer.controller = mController!!
        }

        fun bindData(video: Video, position: Int) {
            mController!!.setTitle(video.title)
            //mController.setLength(video.getLength());

            mVideoPlayer.setUp(video.videoUrl, null)
            //不从上一次位置播放，也就是每次从0播放
            mVideoPlayer.continueFromLastPosition(false)
            mVideoPlayer.postDelayed({ mVideoPlayer.start() }, 50)

            //mVideoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_IJK);
            //mVideoPlayer.setController(mController);
        }

        init {
            mVideoPlayer = itemView.findViewById(R.id.video_player)
        }
    }

}