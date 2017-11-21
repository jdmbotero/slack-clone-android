package com.sena.slackcloneandroid.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.sena.slackcloneandroid.R

class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var textName: TextView = view.findViewById(R.id.textName)
    var textDescription: TextView = view.findViewById(R.id.textDescription)
}