package com.neppplus.colosseum_20210807.datas

import org.json.JSONObject
import java.io.Serializable

class SideData : Serializable {
    var id = 0
    var topicId = 0
    var title = ""
    var voteCount = 0

    companion object {

        fun getSideDataFromJson( jsonObj : JSONObject ) : SideData {

            val sideData = SideData()

            sideData.id = jsonObj.getInt("id")
            sideData.topicId = jsonObj.getInt("topic_id")
            sideData.title = jsonObj.getString("title")
            sideData.voteCount = jsonObj.getInt("vote_count")

            return sideData

        }

    }

}