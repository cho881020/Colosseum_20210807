package com.neppplus.colosseum_20210807

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.neppplus.colosseum_20210807.datas.TopicData
import com.neppplus.colosseum_20210807.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var mTopicData : TopicData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mTopicData = intent.getSerializableExtra("topic") as TopicData

        titleTxt.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageURL).into(topicImg)

        getTopicDetailFromSever()
    }

    fun getTopicDetailFromSever() {

        ServerUtil.getRequestTopicDetailById(mContext, mTopicData.id, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val topicObj = dataObj.getJSONObject("topic")

//                새로 받아온 topic정보를 => mTopicData에 새로 대입. (갱신)

                mTopicData = TopicData.getTopicDataFromJson(topicObj)

//                변경된 mTopicData에 있는 정보들을 => 화면에 반영.
                refreshTopicDataToUI()

            }

        })

    }

    fun refreshTopicDataToUI() {

//        서버에서 받아온 데이터를 -> UI에 반영.
        runOnUiThread {

            titleTxt.text = mTopicData.title
            Glide.with(mContext).load(mTopicData.imageURL).into(topicImg)

        }

    }

}












