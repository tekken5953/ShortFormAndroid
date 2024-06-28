package com.example.shortformandroid.adapter

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.shortformandroid.R
import com.example.shortformandroid.model.DataModel

class VideoViewPagerAdapter(
    private val context: Activity,
    list: ArrayList<DataModel.Video>) :
    RecyclerView.Adapter<VideoViewPagerAdapter.ViewHolder>() {
    private val mList = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewPagerAdapter.ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.video_view_pager_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val videoView: VideoView = view.findViewById(R.id.videoView)

        fun bind(dao: DataModel.Video) {
            dao.let {
                val videoPath = "android.resource://" + context.packageName + "/" + it.videoPath
                val uri = Uri.parse(videoPath)
                videoView.setVideoURI(uri)

//                // Controller
//                val mediaController = MediaController(context)
//                videoView.setMediaController(mediaController)
//                mediaController.setAnchorView(videoView)

                videoView.setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.isLooping = true
                }
            }
        }
    }

    fun setLikeAtPosition(position: Int, b: Boolean) { mList[position].isLike = b }

    fun getLikeAtPosition(position: Int) = mList[position].isLike
}