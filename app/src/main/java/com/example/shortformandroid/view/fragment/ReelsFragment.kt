package com.example.shortformandroid.view.fragment

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.VideoView
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.HandlerCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.shortformandroid.R
import com.example.shortformandroid.adapter.VideoViewPagerAdapter
import com.example.shortformandroid.databinding.FragmentReelsBinding
import com.example.shortformandroid.model.DataModel
import com.example.shortformandroid.view.activity.MainActivity
import kotlin.random.Random

class ReelsFragment : Fragment() {
    private lateinit var binding: FragmentReelsBinding
    private lateinit var parentActivity: MainActivity

    private val videoList = ArrayList<DataModel.Video>()
    private val videoAdapter by lazy { VideoViewPagerAdapter(requireActivity(), videoList) }

    private val videoPathArray = listOf(R.raw.v1,R.raw.v5,R.raw.v6,R.raw.v7,R.raw.v8,R.raw.v1,R.raw.v5,R.raw.v6)
    private val profileArray = listOf(R.drawable.profile,R.drawable.profile2,R.drawable.profile3,R.drawable.profile4)

    private var isLoading = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) parentActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReelsBinding.inflate(inflater, container, false)
        binding.mainVideoViewPager.adapter = videoAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFirst()

        binding.mainVideoViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == videoList.lastIndex && positionOffset <= 0F) {
                    startLoading()
                    HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed({
                        loadMore(position)
                    },1500)
                }
            }

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
                        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.ani_click)
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
                    Glide.with(this@ReelsFragment).load(model.profile).into(binding.mainProfileSmall)
                    Glide.with(this@ReelsFragment).load(model.profile).into(binding.mainProfileBig)

                    if (model.isLike) binding.mainLike.setImageDrawable(getR(R.drawable.like_fill))
                    else binding.mainLike.setImageDrawable(getR(R.drawable.like_empty))
                },300)

                binding.mainFollow.setOnClickListener {
                    binding.mainFollow.apply {
                        if (this.isActivated) {
                            this.text = "Follow"
                            this.setTextColor(requireContext().getColor(R.color.main_white))
                        } else {
                            this.text = "Following"
                            this.setTextColor(requireContext().getColor(R.color.main_blue))
                        }

                        this.isActivated = !this.isActivated
                    }
                }
            }
        })
    }

    private fun loadFirst() {
        for (i in 0 until(5)) {
            videoList.add(DataModel.Video(profileArray.random(), false, "User Name $i",
                "Video Name $i", "Video Info $i", Random.nextInt(0, 20000),
                Random.nextInt(0, 2000), videoPathArray.random()))

            videoAdapter.notifyItemInserted(i)
        }
    }

    private fun loadMore(lastIndex: Int) {
        if (isLoading) {
            for (i in lastIndex + 1 ..lastIndex + 5) {
                videoList.add(DataModel.Video(profileArray.random(), false, "User Name $i",
                    "Video Name $i", "Video Info $i", Random.nextInt(0, 20000),
                    Random.nextInt(0, 2000), videoPathArray.random()))

                videoAdapter.notifyItemInserted(i)
            }

            hideLoading()
        }
    }

    private fun startLoading() {
        isLoading = true
        binding.mainPb.visibility = View.VISIBLE
        binding.mainPb.bringToFront()
        binding.mainVideoViewPager.isEnabled = false
    }

    private fun hideLoading() {
        isLoading = false
        binding.mainPb.visibility = View.GONE
        binding.mainVideoViewPager.isEnabled = true
    }


    private fun getR(res: Int): Drawable? = ResourcesCompat.getDrawable(resources,res,null)

    private fun calcLikeAndComment(value: Int): String =
        if (value > 999) "${parseDoubleToDecimal((value / 1000).toFloat(),1)}K" else value.toString()

    private fun parseDoubleToDecimal(float: Float, @Suppress("SameParameterValue") digit: Int): String = String.format("%.${digit}f", float)
}