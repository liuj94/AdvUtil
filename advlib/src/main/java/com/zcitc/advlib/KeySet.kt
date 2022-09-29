package com.zcitc.advlib




interface KeySet {
    companion object {

        val AUTHORIZATION = "Authorization"
        val ENCRYPTED = "Encrypted"
        val GRANT_TYPE = "grant_type"
        val SCOPE = "scope"
        val CLIENT_ID = "client_id"
        val CONTENT_TYPE = "Content-Type"
        val USERID = "userId"
        val LOGIN_TOKEN = "用于登录和获取验证码"
        val API_TOKEN = "用于内部接口交互"
        val TOKEN_TYPE = "TOKEN的类型"
        val ADVSTATISTICSDATA = "广告点击统计"
        val ADVSTATISTICSDATASTATE = "广告点击统计状态"
        val BUTTONADVSTATISTICSDATASTATE = "Button点击统计状态"
        val BUTTONADVSTATISTICSDATA = "Button点击统计"
        var CONFIGS_VERSION = "广告配置版本号"


        /**
         * 推荐广告集合（金融机构广告、中介广告、中介机构广告、顾问广告）
         */
        const val RECOMMENDED_AD_COLLECTION = "RECOMMENDED_AD_COLLECTION"

        /**
         * 首页金融机构广告
         */
        const val FIRST_PAGE_FINANCIAL_INSTITUTION_ADS = "FIRST_PAGE_FINANCIAL_INSTITUTION_ADS"

        /**
         * 首页中介广告
         */
        const val FIRST_PAGE_AGENT_ADS = "FIRST_PAGE_AGENT_ADS"

        /**
         * 首页中介机构广告
         */
        const val FIRST_PAGE_AGENCY_ADS = "FIRST_PAGE_AGENCY_ADS"

        /**
         * 首页顾问广告
         */
        const val FIRST_PAGE_CONSULTANT_ADS = "FIRST_PAGE_CONSULTANT_ADS"

        /**
         * 首页搜索广告
         */
        const val FIRST_PAGE_SEARCH_ADS = "FIRST_PAGE_SEARCH_ADS"

        /**
         * 启动页广告
         */
        const val FIRST_PAGE_START_ADS = "FIRST_PAGE_START_ADS"

        /**
         * 弹窗广告
         */
        const val FIRST_PAGE_POP_ADS = "FIRST_PAGE_POP_ADS"

        /**
         * 首页中间banner广告
         */
        const val FIRST_PAGE_BANNER_ADS = "FIRST_PAGE_BANNER_ADS"

        /**
         * 首页banner广告
         */
        const val FIRST_PAGE_TOP_CAROUSEL_ADS = "FIRST_PAGE_TOP_CAROUSEL_ADS"

        /**
         * 热门信息区
         */
        const val FIRST_PAGE_BLACK_ADS = "FIRST_PAGE_BLACK_ADS"


    }
}
