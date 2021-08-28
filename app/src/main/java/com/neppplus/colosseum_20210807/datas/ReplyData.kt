package com.neppplus.colosseum_20210807.datas

import org.json.JSONObject

class ReplyData {

    var id = 0
    var content = ""

    var likeCount = 0
    var dislikeCount = 0
    var replyCount = 0

//    작성자 닉네임만 String으로 저장.
    var writerNickname = ""

//    선택된 진영을 selectedSide String으로 저장.
    var selectedSide = ""


    companion object {

        fun getReplyDataFromJson( jsonObj : JSONObject ) : ReplyData {

            val reply = ReplyData()

            reply.id = jsonObj.getInt("id")
            reply.content = jsonObj.getString("content")
            reply.likeCount = jsonObj.getInt("like_count")
            reply.dislikeCount = jsonObj.getInt("dislike_count")
            reply.replyCount = jsonObj.getInt("reply_count")

//            작성자 닉네임 파싱.
            val userObj = jsonObj.getJSONObject("user")
            reply.writerNickname = userObj.getString("nick_name")

//            선택된 진영이 뭔지도 파싱.
            val selectedSideObj = jsonObj.getJSONObject("selected_side")

            reply.selectedSide = selectedSideObj.getString("title")

            return reply

        }

    }

}