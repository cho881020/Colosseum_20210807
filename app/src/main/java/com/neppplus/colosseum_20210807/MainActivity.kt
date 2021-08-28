package com.neppplus.colosseum_20210807

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neppplus.colosseum_20210807.adapters.TopicAdapter
import com.neppplus.colosseum_20210807.datas.TopicData
import com.neppplus.colosseum_20210807.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mTopicList = ArrayList<TopicData>()
    lateinit var mTopicAdapter : TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        topicListView.setOnItemClickListener { parent, view, position, id ->

            val myIntent = Intent(mContext, ViewTopicDetailActivity::class.java)
            startActivity(myIntent)

        }

    }

    override fun setValues() {

//        서버에서 토론주제가 어떤것들이 있는지 요청해서 받아오자.
//        그 받아낸 내용을 -> TopicData형태로 변환해서 -> mTopicList에 담아주자.

        getTopicListFromServer()

        mTopicAdapter = TopicAdapter(mContext, R.layout.topic_list_item, mTopicList)
        topicListView.adapter = mTopicAdapter

    }

//    서버에서 토론 주제 받아오는 함수

    fun getTopicListFromServer() {

        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val topicsArr = dataObj.getJSONArray("topics")

//                0번쨰 {} 부터 ~ 갯수 직전 { } 까지를 반복적으로 파싱.
                for ( i  in  0 until topicsArr.length() ) {

//                    i번째 (위치에 맞는) {  }를 따내고, 내용 분석
                    val topicObj = topicsArr.getJSONObject(i)

//                    TopicData 형태로 topicObj를 변환.
                    val topicData = TopicData()
                    topicData.id = topicObj.getInt("id")
                    topicData.title = topicObj.getString("title")
                    topicData.imageURL = topicObj.getString("img_url")

//                    topicData 변수를 => mTopicList에 추가 등록.
                    mTopicList.add( topicData )

                }

//                리스트뷰의 내용물 새로고침. => UI쓰레드에서 처리.
                runOnUiThread {
                    mTopicAdapter.notifyDataSetChanged()
                }


            }

        })

    }

}