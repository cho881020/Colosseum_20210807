package com.neppplus.colosseum_20210807.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.neppplus.colosseum_20210807.R
import com.neppplus.colosseum_20210807.datas.TopicData

class TopicAdapter(
    val mContext : Context,
    val resId : Int,
    val mList: ArrayList<TopicData>) : ArrayAdapter<TopicData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = mInflater.inflate(R.layout.topic_list_item, null)
        }
        val row = tempRow!!

        val data = mList[position]

        val topicImg = row.findViewById<ImageView>(R.id.topicImg)
        val titleTxt = row.findViewById<TextView>(R.id.titleTxt)

        titleTxt.text = data.title

        Glide.with(mContext).load(data.imageURL).into(topicImg)


        return row
    }

}