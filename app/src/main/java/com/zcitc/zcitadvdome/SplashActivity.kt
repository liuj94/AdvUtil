package com.zcitc.zcitadvdome

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout

class SplashActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this@SplashActivity,MainActivity::class.java))
//        var  layout : RelativeLayout = findViewById<RelativeLayout>(R.id.layout)
//
////        var  splashAdv : AdvView = findViewById<AdvView>(R.id.splashAdv)
//        var  splashAdv : AdvView = AdvView(this)
//        layout.addView(splashAdv)
//        AdvViewUtil().getStartAdvShow(this, splashAdv,object : CountDownAdView.OnCountDownFinishListener{
//            override fun onCountDownFinish() {
//                finish()
//                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
//            }
//
//        },object: AdvClickLisener {
//            override fun onClick(url: String?, title: String?) {
//
//            }
//
//            override fun onClick(url: String?, title: String?, hint: String?) {
//
//            }
//        })
    }
}