package com.example.shortformandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shortformandroid.R
import com.example.shortformandroid.model.DataModel
import com.example.shortformandroid.util.OnAdapterItemSingleClick

/**
 * @author : Lee Jae Young
 * @since : 2023-03-28 오전 11:52
 **/
class PostPhotoAdapter(private val context: Context, list: ArrayList<DataModel.Post>) :
    RecyclerView.Adapter<PostPhotoAdapter.ViewHolder>() {
    private val mList = list

    private lateinit var moreOnClickListener: OnAdapterItemSingleClick

    fun setOnMoreClickListener(listener: OnAdapterItemSingleClick) {
        this.moreOnClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostPhotoAdapter.ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.list_item_post_photo_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img: ImageView = itemView.findViewById(R.id.list_item_post_feed_img)
        private val circle: ImageView = itemView.findViewById(R.id.list_item_post_feed_circle)
        private val more: ImageView = itemView.findViewById(R.id.list_item_post_feed_more)

        fun bind(dao: DataModel.Post) {
            if (!dao.isLast) {
                more.visibility = View.GONE
                circle.visibility = View.VISIBLE
                img.visibility = View.VISIBLE
                dao.img?.let {
                    Glide.with(context).load(it).centerCrop().override(itemView.layoutParams.width,itemView.layoutParams.height).error(R.drawable.error).into(img)

                    circle.bringToFront()

                    itemView.setOnClickListener {
                        if (!dao.isSelected) circle.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                context.resources,
                                R.drawable.circle_fill,
                                null
                            )
                        )
                        else circle.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                context.resources,
                                R.drawable.circle,
                                null
                            )
                        )

                        dao.isSelected = !dao.isSelected
                    }
                }
            } else {
                circle.visibility = View.GONE
                more.visibility = View.VISIBLE
                img.visibility = View.GONE

                itemView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION)
                        kotlin.runCatching { moreOnClickListener.onItemClick(it, position) }
                            .exceptionOrNull()?.stackTraceToString()
                }
            }
        }

    }
}