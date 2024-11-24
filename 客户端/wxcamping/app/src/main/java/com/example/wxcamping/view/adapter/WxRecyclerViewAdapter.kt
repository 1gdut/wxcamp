package com.example.wxcamping.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.wxcamping.R
import com.example.wxcamping.view.activity.GroupActivity

class WxRecyclerViewAdapter(
    private val image: List<Int>,
    private val name: List<String>,
    private val news: List<String>,
    private val time: List<String>,
) : RecyclerView.Adapter<WxRecyclerViewAdapter.WxViewHolder>() {

    inner class WxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.wxlist_icon)
        var name = itemView.findViewById<TextView>(R.id.wxlist_name)
        var news = itemView.findViewById<TextView>(R.id.wxlist_news)
        var time = itemView.findViewById<TextView>(R.id.wxlist_time)
        var constraintLayout = itemView.findViewById<ConstraintLayout>(R.id.wxlist_item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WxRecyclerViewAdapter.WxViewHolder {
        val view = LayoutInflater.from(parent.context)//LayoutInflater是布局加载器
            .inflate(R.layout.wx_list, parent, false)
        return WxViewHolder(view)
    }

    override fun onBindViewHolder(holder: WxRecyclerViewAdapter.WxViewHolder, position: Int) {
        holder.name.text = name[position]
        holder.news.text = news[position]
        holder.time.text = time[position]
        holder.image.setImageResource(image[position])
        holder.constraintLayout.setOnClickListener {
            val intent = Intent(holder.itemView.context, GroupActivity::class.java)
            intent.putExtra("name", name[position])

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return name.size
    }

}