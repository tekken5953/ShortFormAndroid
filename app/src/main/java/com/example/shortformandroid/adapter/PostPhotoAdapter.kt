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

/**
 * @author : Lee Jae Young
 * @since : 2023-03-28 오전 11:52
 **/
class PostPhotoAdapter(private val context: Context, list: ArrayList<DataModel.Post>) :
    RecyclerView.Adapter<PostPhotoAdapter.ViewHolder>() {
    private val mList = list

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

        fun bind(dao: DataModel.Post) {
            Glide.with(context).load(dao.img).into(img)
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
    }
}