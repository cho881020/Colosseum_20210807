package com.neppplus.colosseum_20210807

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neppplus.colosseum_20210807.datas.TopicData
import com.neppplus.colosseum_20210807.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mTopicList = ArrayList<TopicData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

//        서버에서 토론주제가 어떤것들이 있는지 요청해서 받아오자.
//        그 받아낸 내용을 -> TopicData형태로 변환해서 -> mTopicList에 담아주자.

        getTopicListFromServer()

    }

//    서버에서 토론 주제 받아오는 함수

    fun getTopicListFromServer() {

        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {



            }

        })

    }

}