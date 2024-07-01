package com.example.shortformandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shortformandroid.R
import com.example.shortformandroid.model.DataModel

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

        fun bind(dao: DataModel.Feed) {
            userName.text = dao.userName
            Glide.with(context).load(dao.userProfile).into(userProfile)
            Glide.with(context).load(dao.feedImg).into(img)
        }
    }
}