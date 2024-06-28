package com.example.shortformandroid.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.HandlerCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.shortformandroid.R
import com.example.shortformandroid.adapter.VideoViewPagerAdapter
import com.example.shortformandroid.databinding.ActivityMainBinding
import com.example.shortformandroid.model.DataModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val videoList = ArrayList<DataModel.Video>()
    private val videoAdapter by lazy { VideoViewPagerAdapter(this, videoList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainVideoViewPager.adapter = videoAdapter

        val videoPathArray = listOf(R.raw.v1,R.raw.v5,R.raw.v6,R.raw.v7,R.raw.v8,R.raw.v1,R.raw.v5,R.raw.v6)

        videoList.add(DataModel.Video(R.drawable.profile,false, "User Name 1", "Video Name 1", "Video Info 1", 953, 187, videoPathArray[0]))
        videoList.add(DataModel.Video(R.drawable.profile2,false, "User Name 2", "Video Name 2", "Video Info 2", 18560, 1672, videoPathArray[1]))
        videoList.add(DataModel.Video(R.drawable.profile3,false, "User Name 3", "Video Name 3", "Video Info 3", 4961, 580, videoPathArray[2]))
        videoList.add(DataModel.Video(R.drawable.profile4,false, "User Name 4", "Video Name 4", "Video Info 4", 86, 6, videoPathArray[3]))
        videoList.add(DataModel.Video(R.drawable.profile,false, "User Name 5", "Video Name 5", "Video Info 5", 8192, 1142, videoPathArray[4]))
        videoList.add(DataModel.Video(R.drawable.profile2,false, "User Name 6", "Video Name 6", "Video Info 6", 9, 0, videoPathArray[5]))
        videoList.add(DataModel.Video(R.drawable.profile3,false, "User Name 7", "Video Name 7", "Video Info 7", 221, 12, videoPathArray[6]))
        videoList.add(DataModel.Video(R.drawable.profile4,false, "User Name 8", "Video Name 8", "Video Info 8", 3452, 234, videoPathArray[7]))

        videoAdapter.notifyDataSetChanged()

        binding.mainVideoViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            private var previousPosition = -1

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val recyclerView = binding.mainVideoViewPager[0] as RecyclerView
                val currentView = recyclerView.layoutManager?.findViewByPosition(position)
                val previousView = recyclerView.layoutManager?.findViewByPosition(previousPosition)

                currentView?.let {
                    val videoView = it.findViewById<VideoView>(R.id.videoView)
                    videoView?.start()
                }

                previousView?.let {
                    val videoView = it.findViewById<VideoView>(R.id.videoView)
                    videoView?.stopPlayback()
                    videoView?.seekTo(0)
                }

                previousPosition = position

                binding.mainLike.setOnClickListener {
                    if (!videoAdapter.getLikeAtPosition(position)) {
                        val anim = AnimationUtils.loadAnimation(this@MainActivity, R.anim.ani_click)
                        binding.mainLike.startAnimation(anim)
                        videoAdapter.setLikeAtPosition(position, true)
                        binding.mainLike.setImageDrawable(getR(R.drawable.like_fill))
                    }
                    else {
                        videoAdapter.setLikeAtPosition(position, false)
                        binding.mainLike.setImageDrawable(getR(R.drawable.like_empty))
                    }
                }

                val model = videoList[position]

                HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed({
                    binding.mainUserName.text = model.userName
                    binding.mainVideoName.text = model.videoName
                    binding.mainVideoAudioTrack.text = model.videoInfo
                    binding.mainLikeValue.text = calcLikeAndComment(model.likeValue)
                    binding.mainCommentValue.text = calcLikeAndComment(model.commentValue)
                    Glide.with(this@MainActivity).load(model.profile).into(binding.mainProfileSmall)
                    Glide.with(this@MainActivity).load(model.profile).into(binding.mainProfileBig)

                    if (model.isLike) binding.mainLike.setImageDrawable(getR(R.drawable.like_fill))
                    else binding.mainLike.setImageDrawable(getR(R.drawable.like_empty))
                },300)

//                binding.mainFollow.setOnClickListener {
//                    binding.mainFollow.apply {
//                        if (model.) {
//                            this.text = "Following"
//                            this.setTextColor(getColor(R.color.main_blue))
//                            this.setBackgroundResource(R.drawable.follow_enable_bg)
//                        } else {
//                            this.text = "Follow"
//                            this.setTextColor(getColor(R.color.main_white))
//                            this.setBackgroundResource(R.drawable.follow_disable_bg)
//                        }
//                    }
//                }
            }
        })
    }

    private fun getR(res: Int): Drawable? = ResourcesCompat.getDrawable(resources,res,null)

    private fun calcLikeAndComment(value: Int): String =
        if (value > 999) "${parseDoubleToDecimal((value / 1000).toFloat(),1)}K" else value.toString()

    private fun parseDoubleToDecimal(float: Float, digit: Int): String = String.format("%.${digit}f", float)
}