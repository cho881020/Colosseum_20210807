package com.neppplus.colosseum_20210807

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.neppplus.colosseum_20210807.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        emailCheckBtn.setOnClickListener {

//            입력 id 만 추출
            val inputId = emailEdt.text.toString()


//            서버에 중복여부 검사 => UI에 텍스트뷰에 반영.

            ServerUtil.getRequestDuplCheck("EMAIL", inputId, null)

        }

        okBtn.setOnClickListener {

//            입력한 id / pw / 닉네임 추출
            val inputId = emailEdt.text.toString()
            val inputPw = passwordEdt.text.toString()
            val inputNick = nicknameEdt.text.toString()

//            하나라도 입력하지 않았다면, 토스트로 "모든 항목을 입력해주세요." 안내 => 밑의 코드는 실행 X.

            if (inputId == "" || inputPw == "" || inputNick == "") {
                Toast.makeText(mContext, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()

//                밑의 코드는 실행 않도록. => return 활용 함수 강제 종료.
                return@setOnClickListener
            }

//            서버에 세가지 데이터 전달. => ServerUtil 관련 코드 실행

            ServerUtil.putRequestSignUp(inputId, inputPw, inputNick, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

//                    응답을 보고 파싱 -> 화면 처리 진행.

                    val code = jsonObj.getInt("code")
                    if (code == 200) {

//                        가입한 사람 닉네임 + 환영합니다!

                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nickname = userObj.getString("nick_name")

                        runOnUiThread {
                            Toast.makeText(mContext, "${nickname}님 환영 합니다!", Toast.LENGTH_SHORT).show()
                        }

//                        회원가입 화면 종료
                        finish()

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

    override fun setValues() {

    }


}