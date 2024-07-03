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
class PeopleFindAdapter(private val context: Context, list: ArrayList<DataModel.People>) :
    RecyclerView.Adapter<PeopleFindAdapter.ViewHolder>() {
    private val mList = list

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PeopleFindAdapter.ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.list_item_profile_people_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val profile: ImageView = itemView.findViewById(R.id.list_item_people_profile_img)
        private val userName: TextView = itemView.findViewById(R.id.list_item_people_name)
        private val content: TextView = itemView.findViewById(R.id.list_item_people_content)

        fun bind(dao: DataModel.People) {
            Glide.with(context).load(dao.img).into(profile)

            userName.text = dao.name
            content.text = dao.content
        }
    }
}