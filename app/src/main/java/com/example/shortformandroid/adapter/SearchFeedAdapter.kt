package com.example.shortformandroid.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.shortformandroid.R
import com.example.shortformandroid.model.DataModel

/**
 * @author : Lee Jae Young
 * @since : 2023-03-28 오전 11:52
 **/
class SearchFeedAdapter(private val context: Context, list: ArrayList<DataModel.SearchFeed>) :
    RecyclerView.Adapter<SearchFeedAdapter.ViewHolder>() {
    private val mList = list

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchFeedAdapter.ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.list_item_search_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img: ImageView = itemView.findViewById(R.id.list_item_search_img)
        private val copy: ImageView = itemView.findViewById(R.id.list_item_search_copy)

        fun bind(dao: DataModel.SearchFeed) {
            val screenWidth = context.resources.displayMetrics.widthPixels
            val gridColumnWidth = screenWidth / 3 // 3열 그리드 가정

            Glide.with(context)
                .load(dao.img)
                .placeholder(R.color.main_view)
                .error(R.drawable.error)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        val originalWidth = resource.intrinsicWidth
                        val originalHeight = resource.intrinsicHeight
                        Handler(Looper.getMainLooper()).post {
                            val layoutParams = img.layoutParams
                            layoutParams.width = gridColumnWidth
                            layoutParams.height = (gridColumnWidth * originalHeight / originalWidth)
                            img.layoutParams = layoutParams
                            img.setImageDrawable(resource)
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // 필요시 이미지 로드가 취소되었을 때 처리
                        Handler(Looper.getMainLooper()).post {
                            img.setImageDrawable(placeholder)
                        }
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        // 필요시 이미지 로드 실패 시 처리
                        Handler(Looper.getMainLooper()).post {
                            img.setImageDrawable(errorDrawable)
                        }
                    }
                })

            // copy 아이콘 설정
            Glide.with(context)
                .load(if (dao.isReels) R.drawable.reels_fill else R.drawable.copy)
                .into(copy)
        }
    }
}