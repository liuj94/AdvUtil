package com.zcitc.zcitadvdome

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import com.zcitc.advlib.adv.AdvClickLisener
import com.zcitc.advlib.adv.AdvView
import com.zcitc.advlib.adv.AdvViewUtil
import com.zcitc.advlib.adv.CountDownAdView
import com.zcitc.advlib.adv.OnCountDownFinishListener

class SplashActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        startActivity(Intent(this@SplashActivity,MainActivity::class.java))
        var  layout : RelativeLayout = findViewById<RelativeLayout>(R.id.layout)
//        var  splashAdv : AdvView = findViewById<AdvView>(R.id.splashAdv)
//        var  splashAdv : AdvView = AdvView(this)
//        layout.addView(splashAdv)
        AdvViewUtil().getStartAdvShow(this, layout,object : OnCountDownFinishListener {
            override fun onCountDownFinish() {
                finish()
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
            }

        },null)
//            object: AdvClickLisener {
//            override fun onClick(url: String?, title: String?) {
//                Log.d("MainActivity", "url=" + url)
//                startActivity(Intent(this@SplashActivity,WebPageActivity::class.java).putExtra("url",url))
//            }
//
//            override fun onClick(url: String?, title: String?, hint: String?) {
//
//            }
//        })
    }

    override fun onResume() {
        super.onResume()

    }
}