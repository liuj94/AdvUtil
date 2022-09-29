package com.zcitc.zcitadvdome

import android.app.Application
import com.zcitc.advlib.adv.AdvViewUtil

//import com.zcitc.advlib.adv.AdvViewUtil

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AdvViewUtil().initAdv(this)
    }
}