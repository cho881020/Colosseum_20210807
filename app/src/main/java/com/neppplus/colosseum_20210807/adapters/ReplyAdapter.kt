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
import com.neppplus.colosseum_20210807.datas.ReplyData
import com.neppplus.colosseum_20210807.datas.TopicData

class ReplyAdapter(
    val mContext : Context,
    val resId : Int,
    val mList: ArrayList<ReplyData>) : ArrayAdapter<ReplyData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = mInflater.inflate(R.layout.reply_list_item, null)
        }
        val row = tempRow!!

        val data = mList[position]

        val writeNicknameTxt = row.findViewById<TextView>(R.id.writeNicknameTxt)
        val createdAtTxt = row.findViewById<TextView>(R.id.createdAtTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val reReplyTxt = row.findViewById<TextView>(R.id.reReplyTxt)
        val likeTxt = row.findViewById<TextView>(R.id.likeTxt)
        val dislikeTxt = row.findViewById<TextView>(R.id.dislikeTxt)

        val selectedSideTxt = row.findViewById<TextView>(R.id.selectedSideTxt)


        contentTxt.text = data.content

        reReplyTxt.text = "답글 ${data.replyCount}개"
        likeTxt.text = "좋아요 ${data.likeCount}개"
        dislikeTxt.text = "싫어요 ${data.dislikeCount}개"

        writeNicknameTxt.text = data.writerNickname

        selectedSideTxt.text = "(${data.selectedSide})"

        return row
    }

}