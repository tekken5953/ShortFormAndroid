package com.example.shortformandroid.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shortformandroid.R
import com.example.shortformandroid.adapter.SearchFeedAdapter
import com.example.shortformandroid.databinding.FragmentSearchBinding
import com.example.shortformandroid.model.DataModel
import com.example.shortformandroid.view.activity.MainActivity
import kotlin.random.Random

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var parentActivity: MainActivity

    private val searchList = ArrayList<DataModel.SearchFeed>()
    private val searchAdapter by lazy { SearchFeedAdapter(requireContext(), searchList) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) parentActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.searchFeedRv.apply {
            adapter = searchAdapter
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL) // 3열 그리드 레이아웃
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFeed()
    }

    private fun addFeed() {
        val imgArray = listOf<Int?>(R.drawable.feed1,R.drawable.feed2,R.drawable.feed3,
            R.drawable.feed4,R.drawable.feed5,R.drawable.feed6, R.drawable.feed7,R.drawable.feed8,
            R.drawable.feed9,R.drawable.feed10,R.drawable.feed11)
        val trueProbability = 0.3f

        searchList.clear()
        repeat(20) {
            searchList.add(DataModel.SearchFeed(imgArray.random(),nextBooleanWithProbability(trueProbability)))
        }

        searchAdapter.notifyDataSetChanged()
    }

    private fun nextBooleanWithProbability(probability: Float): Boolean {
        return Random.nextFloat() < probability
    }
}