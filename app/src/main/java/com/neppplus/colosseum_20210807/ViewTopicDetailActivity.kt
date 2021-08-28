package com.neppplus.colosseum_20210807

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

//        투표 버튼 이벤트 처리.
//        첫번째 진영, 두번째 진영 버튼 모두 -> 해야하는 일은 같다. (서버에 투표 전달) -> 코드도 같은 내용.

        val onClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {

//                클릭되면 실행할 코드

                Toast.makeText(mContext, v!!.tag.toString(), Toast.LENGTH_SHORT).show()

            }

        }

        firstSideVoteBtn.setOnClickListener(onClickListener)
        secondVoteBtn.setOnClickListener(onClickListener)



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

//            2개 진영에 대해서도 문구 반영
            firstSideTitleTxt.text = mTopicData.sideList[0].title
            firstSideVoteCountTxt.text = "${mTopicData.sideList[0].voteCount}표"

            secondSideTitleTxt.text = mTopicData.sideList[1].title
            secondSideVoteCountTxt.text = "${mTopicData.sideList[1].voteCount}표"

//            2개진영의 버튼에 -> 태그를 설정. -> 각 진영의 id값을 투표버튼의 태그로.
            firstSideVoteBtn.tag = mTopicData.sideList[0].id
            secondVoteBtn.tag = mTopicData.sideList[1].id


        }

    }

}












