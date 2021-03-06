package com.neppplus.colosseum_20210807.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

//    서버유틸 클래스에 응답이 돌아오면 => 각자의 화면에게 넘겨주기 위한 인터페이스.
    interface JsonResponseHandler {
        fun onResponse( jsonObj : JSONObject )
    }

    companion object {

//        어떤 서버로 접속하는지 호스트 주소는 모두 동일함.
//        애초에 변수로 저장해두자.

        val HOST_URL = "http://54.180.52.26"

//        기능별로 함수들 생성. (ex. 로그인용 함수, 회원가입용 함수 등등..)
//        함수 마다 => 기능별 주소는 달라질 수 있다.
//        기능별로 메쏘드의 종류까지 같이 고려해서 함수를 만들자.


//        로그인 수행 함수.

        fun postRequestLogin( id : String, pw : String, handler : JsonResponseHandler? ) {

//            1. 어디로 가야하는가? (주소? => 호스트주소/기능주소-endpoint)

            val urlString = "${HOST_URL}/user"

//            2. 어떤 방식으로 가는가(메쏘드)? POST  (PUT or PATCH)
//             => 서버에 전달해줄 데이터를 어디에 담는가?  (FormBody)

//            3. 어떤 데이터를 담아서 서버에 보내는가? (파라미터)

            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()


//            1,2,3번의 정보 종합해서 => 서버에 접근하는 Request에 대한 총 정리.
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()


//            request에 적힌 정보대로 실제 서버 호출.
//            서버 호출 (CALL) : 서버에 요청 => 클라이언트.

            val client = OkHttpClient()

            client.newCall(request).enqueue( object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    Fail - 실패 : 서버에 연결 자체를 실패한 경우.
//                     인터넷 단선, 서버 터짐. 등등 아예 물리적 연결 실패.
                }

                override fun onResponse(call: Call, response: Response) {

//                    로그인 - 성공 / 비번 틀림 이던 상관 없이, 서버가 나한테 무슨 말이던 응답 (Response) 을 해준 경우.
//                    서버가 나에게 보낸 메세지는 response 변수에 들어있다. => 그 중에 본문 (body) 내용에만 관심을 갖자.

                    val bodyString = response.body!!.string()

//                    bodyString은 한글이 깨져서 알아보기 어렵다.
//                    JSONObject를 통해 변환 => 한글도 제대로 표기.
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

//                    자세한 jsonObj에 대한 처리를 => 화면단에서 알려준 처리 방식대로 실행시키자.
                    handler?.onResponse(jsonObj)

                }

            } )



        }

//        회원가입 수행 함수.

        fun putRequestSignUp( id: String, pw: String, nickname: String, handler: JsonResponseHandler? ) {

            val urlString = "${HOST_URL}/user"

            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .add("nick_name", nickname)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyStr = response.body!!.string()
                    val jsonObj = JSONObject(bodyStr)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)

                }

            })

        }

//        중복확인 수행 함수.

        fun getRequestDuplCheck( type: String, value: String,  handler: JsonResponseHandler? ) {

//            어디로 갈것인가? + 어떤 데이터 (파라미터)를 들고 갈것인가? 한번에 같이 적힌다.

            val url = "${HOST_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
            url.addEncodedQueryParameter("type", type)
            url.addEncodedQueryParameter("value", value)

            val urlString = url.toString()

//            정보 종합 + 실제 메쏘드 지정 (request)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .build()

//            실제 호출 진행.

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }


//        메인화면 (토론 주제)를 받아오는 함수.

        fun getRequestMainInfo(context: Context,   handler: JsonResponseHandler? ) {

            val url = "${HOST_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()
//            url.addEncodedQueryParameter("type", type)
//            url.addEncodedQueryParameter("value", value)

            val urlString = url.toString()

//            정보 종합 + 실제 메쏘드 지정 (request)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

//            실제 호출 진행.

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }



//        특정 토론 주제 상세 현황 (ex. 투표 현황) 받아오기.

        fun getRequestTopicDetailById(context: Context, topicId: Int,  handler: JsonResponseHandler? ) {

            val url = "${HOST_URL}/topic".toHttpUrlOrNull()!!.newBuilder()
            url.addPathSegment(topicId.toString())
            url.addEncodedQueryParameter("order_type", "NEW")
//            url.addEncodedQueryParameter("value", value)

            val urlString = url.toString()
            Log.d("완성된URL", urlString)

//            정보 종합 + 실제 메쏘드 지정 (request)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

//            실제 호출 진행.

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }


//        투표 참가 함수.

        fun postRequestVote(context: Context, sideId: Int, handler : JsonResponseHandler? ) {

            val urlString = "${HOST_URL}/topic_vote"

            val formData = FormBody.Builder()
                .add("side_id", sideId.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()


//            request에 적힌 정보대로 실제 서버 호출.
//            서버 호출 (CALL) : 서버에 요청 => 클라이언트.

            val client = OkHttpClient()

            client.newCall(request).enqueue( object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    Fail - 실패 : 서버에 연결 자체를 실패한 경우.
//                     인터넷 단선, 서버 터짐. 등등 아예 물리적 연결 실패.
                }

                override fun onResponse(call: Call, response: Response) {

//                    로그인 - 성공 / 비번 틀림 이던 상관 없이, 서버가 나한테 무슨 말이던 응답 (Response) 을 해준 경우.
//                    서버가 나에게 보낸 메세지는 response 변수에 들어있다. => 그 중에 본문 (body) 내용에만 관심을 갖자.

                    val bodyString = response.body!!.string()

//                    bodyString은 한글이 깨져서 알아보기 어렵다.
//                    JSONObject를 통해 변환 => 한글도 제대로 표기.
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

//                    자세한 jsonObj에 대한 처리를 => 화면단에서 알려준 처리 방식대로 실행시키자.
                    handler?.onResponse(jsonObj)

                }

            } )



        }

    }

}