package com.example.shortformandroid.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shortformandroid.R
import com.example.shortformandroid.model.DataModel
import com.example.shortformandroid.view.activity.UserPageActivity

/**
 * @author : Lee Jae Young
 * @since : 2023-03-28 오전 11:52
 **/
class HomeFeedAdapter(private val context: Context, list: ArrayList<DataModel.Feed>) :
    RecyclerView.Adapter<HomeFeedAdapter.ViewHolder>() {
    private val mList = list

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeFeedAdapter.ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.list_item_feed_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userName: TextView = itemView.findViewById(R.id.list_item_feed_user_name)
        private val userProfile: ImageView = itemView.findViewById(R.id.list_item_feed_profile)
        private val img: ImageView = itemView.findViewById(R.id.list_item_feed_img)
        private val commentPreview: TextView = itemView.findViewById(R.id.list_item_feed_comment_preview)
        private val commentViewAll: TextView = itemView.findViewById(R.id.list_item_comment_all_view)

        fun bind(dao: DataModel.Feed) {
            userName.text = dao.userName
            commentPreview.text = getSpan()

            Glide.with(context).load(dao.userProfile).into(userProfile)
            Glide.with(context).load(dao.feedImg).into(img)

            userProfile.setOnClickListener { goUserPage(dao.userName) }
            userName.setOnClickListener { goUserPage(dao.userName) }
        }

        private fun goUserPage(userName: String?) {
            if (context is Activity && context != UserPageActivity())
                Intent(context, UserPageActivity::class.java).apply {
                    putExtra("user_name", userName)
                    context.startActivity(this)
                }
        }

        private fun getSpan(): SpannableStringBuilder {
            val text = "User1  wow! I love this post"
            val span = SpannableStringBuilder(text)
            span.setSpan(StyleSpan(Typeface.BOLD), 0, "User1".length + 1 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            return span
        }
    }
}