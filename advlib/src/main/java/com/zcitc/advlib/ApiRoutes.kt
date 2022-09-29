package com.zcitc.advlib
interface ApiRoutes {

    companion object {
//        var address: String = BuildConfig.ADDRESS
//        val BASE_GATEWAY = if(address.equals("本地")) "http://gateway.zchs.work" else "https://gateway.fyitc.com"
//        val BASE_WB_CNNBFDC2 = if(address.equals("本地")) "http://fang-gz.k8s.zchs.work/fang-gz/app" else "https://fang-gz.fyitc.com/fang-gz/app"
         val BASE_GATEWAY = "https://gateway.fyitc.com"
        val BASE_WB_CNNBFDC2 =   "https://fang-gz.fyitc.com/fang-gz/app"

        val BASE_API2 = "/nbfc/gz/2.0/api"


        /** 推荐广告配置*/
        val ADVFIRSTPAGEPARTY = "$BASE_GATEWAY$BASE_API2/menu/list?groupName=firstpage_party"

        /**
         * 获取广告
         */
        val ALLAD = "$BASE_GATEWAY$BASE_API2/a/adv-item/configs?version="
        val ADVSDISPLAYRECORD = "$BASE_GATEWAY$BASE_API2/a/display-record/multiple"
        val ADVSCLICKRECORD = "$BASE_GATEWAY$BASE_API2/a/click-record/multiple"
        val BUTTONCLICKRECORD = "$BASE_GATEWAY$BASE_API2/a/button-click-record/multiple"
        val TOKEN = "https://sso.fyitc.com/connect/token"

        /**
         * 搜索页
         */
        val HOUSESEARCH = "$BASE_WB_CNNBFDC2/house/search"

    }

}
