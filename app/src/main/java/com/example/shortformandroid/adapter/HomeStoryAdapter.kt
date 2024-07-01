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
import com.example.shortformandroid.util.OnAdapterItemSingleClick

/**
 * @author : Lee Jae Young
 * @since : 2023-03-28 오전 11:52
 **/
class HomeStoryAdapter(private val context: Context, list: ArrayList<DataModel.Story>) :
    RecyclerView.Adapter<HomeStoryAdapter.ViewHolder>() {
    private val mList = list

    private lateinit var onClickListener: OnAdapterItemSingleClick

    fun setOnItemClickListener(listener: OnAdapterItemSingleClick) {
        this.onClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeStoryAdapter.ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.list_item_story_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = itemView.findViewById(R.id.list_item_story_text)
        private val img: ImageView = itemView.findViewById(R.id.list_item_story_img)


        fun bind(dao: DataModel.Story) {
            name.text = dao.userName
            Glide.with(context).load(dao.userProfile).into(img)

            val position = adapterPosition

            itemView.setOnClickListener {
                kotlin.runCatching {
                    if (position != RecyclerView.NO_POSITION) onClickListener.onItemClick(it, position)
                }.exceptionOrNull()?.printStackTrace()
            }
        }
    }
}