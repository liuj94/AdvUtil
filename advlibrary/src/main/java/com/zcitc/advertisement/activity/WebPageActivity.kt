package com.zcitc.advertisement.activity

import android.content.Intent.getIntent
import android.os.Bundle
import com.zcitc.advertisement.R
import com.zcitc.advertisement.databinding.ActivityWebBinding
import com.zcitc.baselibrary.base.BaseActivity
import com.zcitc.baselibrary.base.BaseVModel

class WebPageActivity : BaseActivity<BaseVModel,ActivityWebBinding>(ActivityWebBinding::inflate) {
    override fun getViewModel(): Class<BaseVModel> = BaseVModel::class.java
    override fun initData(savedInstanceState: Bundle?) {
      var urlStr :String? = getIntent().getStringExtra("weburl")
        urlStr?.let { mBinding.webview.loadUrl(it) }

    }

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_web

}