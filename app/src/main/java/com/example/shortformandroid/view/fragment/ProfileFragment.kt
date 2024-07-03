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
import com.example.shortformandroid.adapter.PeopleFindAdapter
import com.example.shortformandroid.adapter.VideoViewPagerAdapter
import com.example.shortformandroid.databinding.FragmentProfileBinding
import com.example.shortformandroid.databinding.FragmentReelsBinding
import com.example.shortformandroid.model.DataModel
import com.example.shortformandroid.view.activity.MainActivity
import kotlin.random.Random

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var parentActivity: MainActivity

    private val peopleList = ArrayList<DataModel.People>()
    private val peopleAdapter by lazy { PeopleFindAdapter(requireContext(), peopleList) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) parentActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.profilePeopleRv.adapter = peopleAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        peopleList.add(DataModel.People(R.drawable.profile1,"User1","followed user2 and 8 people"))
        peopleList.add(DataModel.People(R.drawable.profile2,"User2","followed user2 and 8 people"))
        peopleList.add(DataModel.People(R.drawable.profile3,"User3","followed user2 and 8 people"))
        peopleList.add(DataModel.People(R.drawable.profile4,"User4","followed user2 and 8 people"))
        peopleList.add(DataModel.People(R.drawable.profile1,"User5","followed user2 and 8 people"))

        peopleAdapter.notifyDataSetChanged()
    }
}