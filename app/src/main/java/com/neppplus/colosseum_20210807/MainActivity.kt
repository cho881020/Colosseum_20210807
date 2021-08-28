package com.neppplus.colosseum_20210807

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.neppplus.colosseum_20210807.adapters.TopicAdapter
import com.neppplus.colosseum_20210807.datas.TopicData
import com.neppplus.colosseum_20210807.utils.ContextUtil
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

        logoutBtn.setOnClickListener {

//            로그인? id/pw을 서버에 보내서 -> 회원이 맞는 검사 -> 성공했다면 토큰을 받는다. -> 기기에 저장.
//            로그아웃? 저장되어있는 토큰을 날려주고 -> 다시 로그인 화면으로 이동.


//            메세지만 "정말 로그아웃 하시겠습니까?" 확인 -> 진짜 로그아웃 처리, 취소 -> 아무일도 없게.

            val alert = AlertDialog.Builder(mContext)
//            alert.setTitle("로그아웃 확인")
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
//                진짜 로그아웃 처리

                ContextUtil.setToken(mContext, "")

                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)
                finish()

            })
            alert.setNegativeButton("취소", null)

            alert.show()




        }

        topicListView.setOnItemClickListener { parent, view, position, id ->

//            클릭된 주제가 뭔지?
            val clickedTopic = mTopicList[position]

            val myIntent = Intent(mContext, ViewTopicDetailActivity::class.java)
            myIntent.putExtra("topic", clickedTopic)
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
                    val topicData = TopicData.getTopicDataFromJson(topicObj)


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