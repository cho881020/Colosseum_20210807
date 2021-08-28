package com.neppplus.colosseum_20210807.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

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

//    글이 작성된 일시를 Calendar 형태로 저장.
    val createdAt = Calendar.getInstance()  // 기본값 : 현재 시간이 자동으로 기록됨. -> 서버에서 알려주는 댓글 작성 일시로 데이터 변경.

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


//            서버가 알려주는 작성 일시 (String) 를 받아서 -> SimpleDateFormat 이용 -> Calendar형태로 담자.

            val sdf = SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" )

            val serverTimeStr = jsonObj.getString("created_at")


//            sdf로 서버시간Str 파싱 => 댓글의.작성일시의.시간데이터로 대입.
            reply.createdAt.time = sdf.parse(serverTimeStr)


            return reply

        }

    }

}