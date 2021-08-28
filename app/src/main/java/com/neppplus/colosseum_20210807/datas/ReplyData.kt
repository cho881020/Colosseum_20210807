package com.neppplus.colosseum_20210807.datas

import org.json.JSONObject

class ReplyData {

    var id = 0
    var content = ""

    var likeCount = 0
    var dislikeCount = 0
    var replyCount = 0


    companion object {

        fun getReplyDataFromJson( jsonObj : JSONObject ) : ReplyData {

            val reply = ReplyData()

            reply.id = jsonObj.getInt("id")
            reply.content = jsonObj.getString("content")
            reply.likeCount = jsonObj.getInt("like_count")
            reply.dislikeCount = jsonObj.getInt("dislike_count")
            reply.replyCount = jsonObj.getInt("reply_count")

            return reply

        }

    }

}