package com.neppplus.colosseum_20210807

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.neppplus.colosseum_20210807.adapters.ReplyAdapter
import com.neppplus.colosseum_20210807.datas.ReplyData
import com.neppplus.colosseum_20210807.datas.TopicData
import com.neppplus.colosseum_20210807.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var mTopicData : TopicData

    val mReplyList = ArrayList<ReplyData>()
    lateinit var mReplyAdapter : ReplyAdapter

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
//                서버에 투표 요청 => 응답 돌아오면 => 화면 새로고침

//                몇번 진영? 태그에서 Int로 변환.
                val sideId =  v!!.tag.toString().toInt()

                ServerUtil.postRequestVote(mContext, sideId, object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {

                        val code = jsonObj.getInt("code")

                        if (code == 200) {

//                            투표에 성공 케이스. => 서버에서 새로 받아와서, 화면에 반영. (득표수 변경)

//                            밑의 함수에 서버에서 데이터 수신 + 화면에 새로 반영 모두 작성되어있다.
                            getTopicDetailFromSever()

                        }
                        else {
                            val message = jsonObj.getString("message")

                            runOnUiThread {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }

                        }

                    }

                })


            }

        }

        firstSideVoteBtn.setOnClickListener(onClickListener)
        secondVoteBtn.setOnClickListener(onClickListener)



    }

    override fun setValues() {

        mTopicData = intent.getSerializableExtra("topic") as TopicData

        titleTxt.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageURL).into(topicImg)

        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        replyListView.adapter = mReplyAdapter

        getTopicDetailFromSever()
    }

    fun getTopicDetailFromSever() {

        ServerUtil.getRequestTopicDetailById(mContext, mTopicData.id, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val topicObj = dataObj.getJSONObject("topic")

//                새로 받아온 topic정보를 => mTopicData에 새로 대입. (갱신)

                mTopicData = TopicData.getTopicDataFromJson(topicObj)

//                댓글 목록도 같이 내려줌. => 파싱해서, 목록에 담아주자.

                val repliesArr =  topicObj.getJSONArray("replies")

                for  ( i   in 0 until  repliesArr.length()) {

                    mReplyList.add( ReplyData.getReplyDataFromJson(  repliesArr.getJSONObject(i)  ) )

                }

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


//            댓글 목록 어댑터 새로고침
            mReplyAdapter.notifyDataSetChanged()

        }

    }

}












