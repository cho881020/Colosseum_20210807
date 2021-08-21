package com.neppplus.colosseum_20210807

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.neppplus.colosseum_20210807.utils.ContextUtil
import com.neppplus.colosseum_20210807.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        autoLoginCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->

//            지금 체크된 값 (true / false) 을 ContextUtil을 이용해, 기기에 반영구 저장.
            ContextUtil.setAutoLogin(mContext, isChecked)

        }

        signUpBtn.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

        loginBtn.setOnClickListener {
            val inputId = idEdt.text.toString()
            val inputPw = pwEdt.text.toString()

//            이 아이디,비번을 들고 => 서버에 진짜 회원이 맞는지 문의. (로그인)

            ServerUtil.postRequestLogin(inputId, inputPw, object : ServerUtil.JsonResponseHandler {

                override fun onResponse(jsonObj: JSONObject) {

//                    화면입장에서, 로그인 시도에 대한 결과 처리 코드.

                    val code = jsonObj.getInt("code")

//                    code : 200 => 모든 로직 성공적. (로그인 성공)
//                    code : 400, 404, 403, 500 등등 200이 아닌숫자 => 다양한 이유로 로그인 실패.

                    if (code == 200) {
//                        임시 : 로그인한 사람의 닉네임을 토스트로 출력.

                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nickname = userObj.getString("nick_name")

                        runOnUiThread {
                            Toast.makeText(mContext, "${nickname}님 환영합니다!", Toast.LENGTH_SHORT).show()
                        }

//                        서버가 내려주는 토큰 저장.
                        val token = dataObj.getString("token")
//                        ContextUtil 이용해 저장하자.
                        ContextUtil.setToken(mContext, token)


//                        메인화면으로 이동
                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
//                        로그인화면 종료
                        finish()

                    }
                    else {
//                        로그인에 왜 실패했는지 사유를 토스트로 출력.

                        val message = jsonObj.getString("message")

//                        UI동작을 메인이 아니라, 백그라운드에서 돌리면 => 접근 차단, 앱 강제종료.

                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                        }


                    }

                }

            })

        }


    }

    override fun setValues() {

//        화면에 보여지는 데이터들을 세팅하는 코드.
        autoLoginCheckBox.isChecked = ContextUtil.getAutoLogin(mContext)

    }

}