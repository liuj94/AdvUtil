package com.zcitc.advlib.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.zcitc.advlib.R
import com.zcitc.advlib.adv.AdvViewUtil

class WebPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zc_activity_web)
        var urlStr : String? = intent.getStringExtra("weburl")
        var webview = findViewById<WebView>(R.id.webview)
        urlStr?.let { webview.loadUrl(urlStr) }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        AdvViewUtil().ADV_FINISH()
    }
}