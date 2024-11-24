package com.example.wxcamping.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.wxcamping.R
import com.example.wxcamping.model.Model
import com.example.wxcamping.model.entity.Message
import com.example.wxcamping.viewmodel.GroupViewModel

class ChatRecyclerAdapter(
    private val messages: List<Message>
) : RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.chat_list_name)
        var content = itemView.findViewById<TextView>(R.id.chat_list_content)
        var mycontent = itemView.findViewById<TextView>(R.id.chat_list_mycontent)
        var left = itemView.findViewById<ConstraintLayout>(R.id.chat_list_left)
        var right = itemView.findViewById<ConstraintLayout>(R.id.chat_list_right)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)//LayoutInflater是布局加载器
            .inflate(R.layout.chat_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatRecyclerAdapter.ViewHolder, position: Int) {
        val message = messages[position]
        if (message.isMyMessage) {
            holder.right.visibility = View.VISIBLE
            holder.left.visibility = View.GONE
            holder.mycontent.text = message.content
        } else {
            holder.right.visibility = View.GONE
            holder.left.visibility = View.VISIBLE
            holder.name.text = message.username
            holder.content.text = message.content
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

}