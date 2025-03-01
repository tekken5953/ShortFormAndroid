package com.example.shortformandroid.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shortformandroid.R
import com.example.shortformandroid.adapter.HomeFeedAdapter
import com.example.shortformandroid.adapter.HomeStoryAdapter
import com.example.shortformandroid.databinding.FragmentHomeBinding
import com.example.shortformandroid.model.DataModel
import com.example.shortformandroid.view.activity.MainActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var parentActivity: MainActivity

    private val feedList = ArrayList<DataModel.Feed>()
    private val storyList = ArrayList<DataModel.Story>()

    private val feedAdapter by lazy { HomeFeedAdapter(parentActivity, feedList) }
    private val storyAdapter by lazy { HomeStoryAdapter(requireContext(), storyList) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) parentActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeFeedRv.adapter = feedAdapter
        binding.homeStoryRv.adapter = storyAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addStory(DataModel.Story(R.drawable.profile2,"user1"))
        addStory(DataModel.Story(R.drawable.profile3, "user2"))
        addStory(DataModel.Story(R.drawable.profile4, "user3"))
        addStory(DataModel.Story(R.drawable.profile1, "user4"))
        addStory(DataModel.Story(R.drawable.profile2, "user5"))
        addStory(DataModel.Story(R.drawable.profile3, "user6"))
        addStory(DataModel.Story(R.drawable.profile4, "user7"))

        addFeed(DataModel.Feed("user1", R.drawable.profile1,R.drawable.feed1))
        addFeed(DataModel.Feed("user2", R.drawable.profile2,R.drawable.feed2))
        addFeed(DataModel.Feed("user3", R.drawable.profile3,R.drawable.feed3))
        addFeed(DataModel.Feed("user4", R.drawable.profile4,R.drawable.feed4))
        addFeed(DataModel.Feed("user5", R.drawable.profile1,R.drawable.feed5))
        addFeed(DataModel.Feed("user6", R.drawable.profile2,R.drawable.feed6))
        addFeed(DataModel.Feed("user7", R.drawable.profile3,R.drawable.feed7))
        addFeed(DataModel.Feed("user8", R.drawable.profile4,R.drawable.feed8))
        addFeed(DataModel.Feed("user9", R.drawable.profile1,R.drawable.feed9))
    }

    private fun addStory(item: DataModel.Story) {
        storyList.add(item)
        storyAdapter.notifyItemInserted(storyList.lastIndex)
    }

    private fun addFeed(item: DataModel.Feed) {
        feedList.add(item)
        feedAdapter.notifyItemInserted(feedList.lastIndex)
    }
}