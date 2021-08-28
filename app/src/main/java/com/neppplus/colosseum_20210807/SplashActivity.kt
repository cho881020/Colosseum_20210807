package com.neppplus.colosseum_20210807

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.neppplus.colosseum_20210807.utils.ContextUtil

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

//        2.5초 뒤에  => (자동로그인 조건이 OK라면) 메인으로 이동
//                   => 로그인이 다시 필요하다면 => 로그인 화면으로 이동.


        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed( {
//            자동 로그인 해도 되는지?
//            1. 자동로그인을 한다고 체크 되어있는지?
//            2. 토큰값도 기기에 저장되어 있는지?  AND
//            3. (skip) 이 토큰이 정말 로그인이 되는 토큰인지? => 토큰 검증.

            val myIntent : Intent

            if ( ContextUtil.getAutoLogin(mContext) && ContextUtil.getToken(mContext) != "" ) {
//                메인화면으로
                myIntent = Intent(mContext, MainActivity::class.java)

            }
            else {
//                로그인 필요. 로그인화면으로 이동
                myIntent = Intent(mContext, LoginActivity::class.java)

            }

            startActivity(myIntent)
            finish()


        }, 2500 )



    }

}










