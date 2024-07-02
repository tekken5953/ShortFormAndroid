package com.example.shortformandroid.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.shortformandroid.R
import com.example.shortformandroid.adapter.HomeFeedAdapter
import com.example.shortformandroid.model.DataModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @author : Lee Jae Young
 * @since : 2023-03-28 오전 11:15
 **/

class FeedDialogClass(context: Context, private val index: Int?,private val title: String) {
    private var builder: AlertDialog.Builder =
        AlertDialog.Builder(context, R.style.AlertDialog)
    private val view by lazy { LayoutInflater.from(context).inflate(R.layout.dialog_feed,null) }
    private val alertDialog: AlertDialog by lazy { builder.create() }

    private val feedList = ArrayList<DataModel.Feed>()
    private val feedAdapter by lazy { HomeFeedAdapter(context, feedList) }

    /** 다이얼로그 뒤로가기 버튼 리스너 등록 **/
    fun setBackPressed(imageView: View): FeedDialogClass {
        imageView.setOnClickListener { dismiss() }
        return this
    }

    /** 다이얼로그 뷰 소멸 **/
    fun dismiss() { if (alertDialog.isShowing) alertDialog.dismiss() }

    /** 다이얼로그 뷰 갱신 **/
    fun show(cancelable: Boolean) {
        view.let {
            if (view.parent != null) (view.parent as ViewGroup).removeView(view)
            builder.setView(view).setCancelable(cancelable)
            alertDialog.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initAdapter(): FeedDialogClass {
        MainScope().launch {
            val rv =  view.findViewById<RecyclerView>(R.id.dialog_feed_rv)
            rv.adapter = feedAdapter
            addFeed(DataModel.Feed("user1", R.drawable.profile1,R.drawable.feed1))
            addFeed(DataModel.Feed("user2", R.drawable.profile2,R.drawable.feed2))
            addFeed(DataModel.Feed("user3", R.drawable.profile3,R.drawable.feed3))
            addFeed(DataModel.Feed("user4", R.drawable.profile4,R.drawable.feed4))
            addFeed(DataModel.Feed("user5", R.drawable.profile1,R.drawable.feed5))
            addFeed(DataModel.Feed("user6", R.drawable.profile2,R.drawable.feed6))
            addFeed(DataModel.Feed("user7", R.drawable.profile3,R.drawable.feed7))
            addFeed(DataModel.Feed("user8", R.drawable.profile4,R.drawable.feed8))
            addFeed(DataModel.Feed("user9", R.drawable.profile1,R.drawable.feed9))

            kotlin.runCatching { index?.let { rv.smoothScrollToPosition(it) } }
                .exceptionOrNull()?.stackTraceToString()

            val cancelIv: ImageView = view.findViewById(R.id.dialog_feed_back)
            cancelIv.setOnClickListener { dismiss() }

            val titleTv: TextView = view.findViewById(R.id.dialog_feed_title)
            titleTv.text = title
        }

        return this
    }

    private fun addFeed(item: DataModel.Feed) {
        feedList.add(item)
        feedAdapter.notifyItemInserted(feedList.lastIndex)
    }
}