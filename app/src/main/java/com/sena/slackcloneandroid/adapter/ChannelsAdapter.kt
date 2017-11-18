package com.sena.slackcloneandroid.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sena.slackcloneandroid.R
import com.sena.slackcloneandroid.adapter.viewholder.ChannelViewHolder
import com.sena.slackcloneandroid.model.Channel
import com.sena.slackcloneandroid.model.Data

class ChannelsAdapter constructor(items: List<Data<Channel>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<Data<Channel>> = items
    set(items) {
        field = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_channel, parent, false)

        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        val position = holder.adapterPosition
        val item = items[position]
        val viewHolder = holder as ChannelViewHolder

        viewHolder.textName.text = item.attributes!!.name
        viewHolder.textDescription.text = item.attributes!!.description
    }
}