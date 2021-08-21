package com.neppplus.colosseum_20210807

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

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


        }

    }

    override fun setValues() {

    }


}