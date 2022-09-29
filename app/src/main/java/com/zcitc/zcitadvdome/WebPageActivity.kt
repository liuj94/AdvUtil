package com.zcitc.zcitadvdome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import com.zcitc.advlib.adv.AdvViewUtil

class WebPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_page)
        var urlStr : String? = intent.getStringExtra("url")
        var webview = findViewById<WebView>(R.id.webview)
        urlStr?.let { webview.loadUrl(urlStr) }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        AdvViewUtil().ADV_FINISH()
    }
}