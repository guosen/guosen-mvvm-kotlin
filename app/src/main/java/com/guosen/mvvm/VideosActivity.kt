package com.guosen.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.guosen.common.widget.wraprv.WrapRecyclerView
import com.guosen.common.widget.vrecycle.OnPagerListener
import com.guosen.common.widget.vrecycle.PagerLayoutManager
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager
import org.yczbj.ycvideoplayerlib.player.VideoPlayer


/**
 * <pre>
 *     author : guosenlin
 *     e-mail : guosenlin91@gmail.com
 *     time   : 2021/01/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Route(path = "/app/video")
class VideosActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        val rv : WrapRecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = PagerLayoutManager(this,OrientationHelper.VERTICAL)
        val list: MutableList<Video> = ArrayList()
        var lvideos = DataProvider.VideoPlayerList
        for (a in DataProvider.VideoPlayerList.indices) {
            val video = Video(
                DataProvider.VideoPlayerTitle[a],
                10, "", DataProvider.VideoPlayerList[a]
            )
            list.add(video)
        }


        val mAdapter = VideoAdapter(this, list)

        /**
         * 头部布局如果只有外层一层显示不出。
         */
        var head = LayoutInflater.from(this).inflate(R.layout.header_view,null,false)

        rv.setAdapter(mAdapter)
        rv.addHeader(head)
        rv.setLayoutManager(layoutManager)
        layoutManager.setOnViewPagerListener(object : OnPagerListener {
            override fun onInitComplete() {
                println("OnPagerListener---onInitComplete--" + "初始化完成")
            }

            override fun onPageRelease(isNext: Boolean, position: Int) {
                println("OnPagerListener---onPageRelease--$position-----$isNext")
            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                println("OnPagerListener---onPageSelected--$position-----$isBottom")
            }
        })
        rv.setRecyclerListener(RecyclerView.RecyclerListener { holder ->
            val videoPlayer: VideoPlayer = (holder as VideoAdapter.VideoViewHolder).mVideoPlayer
            if (videoPlayer === VideoPlayerManager.instance().currentVideoPlayer) {
                VideoPlayerManager.instance().releaseVideoPlayer()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        VideoPlayerManager.instance().releaseVideoPlayer()

    }

    override fun onBackPressed() {
        VideoPlayerManager.instance().onBackPressed()
        super.onBackPressed()

    }
}