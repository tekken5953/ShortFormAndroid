package com.example.shortformandroid.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.shortformandroid.R
import com.example.shortformandroid.adapter.UserFeedAdapter
import com.example.shortformandroid.databinding.ActivityUserPageBinding
import com.example.shortformandroid.model.DataModel
import com.example.shortformandroid.util.OnAdapterItemSingleClick
import com.example.shortformandroid.view.dialog.FeedDialogClass
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class UserPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserPageBinding

    private val userFeedList = ArrayList<DataModel.Feed>()
    private val userFeedAdapter by lazy { UserFeedAdapter(this, userFeedList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userGridRv.adapter = userFeedAdapter
        binding.userBack.setOnClickListener { finish() }

        addGridArray(24)

        // 탭 선택 리스너 설정
        binding.userTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position) {
                    0 -> addGridArray(24)
                    1 -> addGridArray(5)
                    2 -> addGridArray(8)
                    3 -> addGridArray(12)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        userFeedAdapter.setOnItemClickListener(object : OnAdapterItemSingleClick() {
            override fun onSingleClick(v: View?, position: Int) {
                FeedDialogClass(this@UserPageActivity, position, "Posts").initAdapter().show(true)
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addGridArray(count: Int) {
        userFeedList.clear()

        val feedArray = arrayOf(R.drawable.feed1,R.drawable.feed2,R.drawable.feed3,R.drawable.feed4,
            R.drawable.feed5,R.drawable.feed6,R.drawable.feed7,R.drawable.feed8,R.drawable.feed9)

        repeat(count) { addGridFeed(feedArray.random()) }

        userFeedAdapter.notifyDataSetChanged()
    }

    private fun addGridFeed(res: Int?) =
        userFeedList.add(DataModel.Feed(intent.extras?.getString("user_name") ?: "Unknown", null, res))
}