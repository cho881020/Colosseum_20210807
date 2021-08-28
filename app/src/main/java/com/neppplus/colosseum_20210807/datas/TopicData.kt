package com.neppplus.colosseum_20210807.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData : Serializable {

    var id = 0 // Int가 들어온다는 명시.
    var title = "" // String이 온다는 명시.
    var imageURL = ""

    companion object {

//        JSONObject를 적당한 형태로 넣으면  -> 내용을 파싱해서 -> TopicData 객체로 돌려주는 함수.

        fun getTopicDataFromJson( jsonObj : JSONObject ) : TopicData {

//            결과로 내보낼 TopicData 객체를 준비.

            val resultTopicData = TopicData()

//            결과로 나가기 전에, jsonObj 내부의 값들을 따내서 -> resultTopicData의 변수에 대입.
            resultTopicData.id = jsonObj.getInt("id")
            resultTopicData.title = jsonObj.getString("title")
            resultTopicData.imageURL = jsonObj.getString("img_url")


//            최종 결과로 선정
            return resultTopicData
        }

    }

}
