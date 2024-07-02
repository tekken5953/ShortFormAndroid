package com.example.shortformandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shortformandroid.R
import com.example.shortformandroid.model.DataModel
import com.example.shortformandroid.util.OnAdapterItemSingleClick

/**
 * @author : Lee Jae Young
 * @since : 2023-03-28 오전 11:52
 **/
class UserFeedAdapter(private val context: Context, list: ArrayList<DataModel.Feed>) :
    RecyclerView.Adapter<UserFeedAdapter.ViewHolder>() {
    private val mList = list

    private lateinit var onClickListener: OnAdapterItemSingleClick

    fun setOnItemClickListener(listener: OnAdapterItemSingleClick) {
        this.onClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserFeedAdapter.ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.list_item_user_feed_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img: ImageView = itemView.findViewById(R.id.list_item_user_feed_img)

        fun bind(dao: DataModel.Feed) {
            Glide.with(context).load(dao.feedImg).into(img)

            val position = adapterPosition

            itemView.setOnClickListener {
                kotlin.runCatching {
                    if (position != RecyclerView.NO_POSITION) onClickListener.onItemClick(it, position)
                }.exceptionOrNull()?.printStackTrace()
            }
        }
    }
}