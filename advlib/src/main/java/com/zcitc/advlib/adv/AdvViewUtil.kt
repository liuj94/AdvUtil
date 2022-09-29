package com.zcitc.advlib.adv

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.lzy.okgo.OkGo
import com.lzy.okgo.https.HttpsUtils
import com.lzy.okgo.model.HttpHeaders
import com.zcitc.advlib.KeySet
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_AGENCY_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_AGENT_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_BANNER_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_BLACK_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_CONSULTANT_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_FINANCIAL_INSTITUTION_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_POP_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_SEARCH_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_START_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_TOP_CAROUSEL_ADS
import com.zcitc.advlib.KeySet.Companion.RECOMMENDED_AD_COLLECTION
import com.zcitc.advlib.activity.WebPageActivity
import com.zcitc.advlib.bean.ADPlanItemsData
import com.zcitc.advlib.utils.LocalDataUtils
import com.zcitc.advlib.utils.RefreshTokenUtils
import com.zcitc.advlib.utils.isNullOrEmptyStr
import com.zcitc.glidelibrary.GlideUtils
import com.zcitc.utilslibrary.FastClickUtils
import com.zcitc.utilslibrary.utils.LiveDataBus
import okhttp3.OkHttpClient

class AdvViewUtil {


    var firstOpen = true
    /**
     * 初始化广告
     */
    fun initAdv(application: Application) {
        initHttp(application)
//        clearAdvCache(application)
    }

    val userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.43 BIDUBrowser/6.x Safari/537.31"
    private fun initHttp(application: Application) {
        val builder = OkHttpClient.Builder()
        val sslParams1 = HttpsUtils.getSslSocketFactory()
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager)
        var headers: HttpHeaders = HttpHeaders()
        headers.put("User-Agent", userAgent)
        headers.put("platform", "1")
        headers.put("app-version", "1.0")
        headers.put("api-version", "1")
        OkGo.getInstance().init(application).setOkHttpClient(builder.build())
            .addCommonHeaders(headers)
    }

    fun getAdvDataAddShow(
        activity: FragmentActivity, advView: ViewGroup?,
        tagData: String, onAdvClickLisener: AdvClickLisener?
    ) {
        getAdvDataAddShow(activity, advView, tagData,  null,onAdvClickLisener)
    }


    fun getAdvDataAddShow(
        activity: FragmentActivity, advView: ViewGroup?,
        tagData: String, onCountDownFinishListener: OnCountDownFinishListener?,onAdvClickLisener: AdvClickLisener?
    ) {
        RefreshTokenUtils.requestToken(activity) {
            var configsVersion = LocalDataUtils().getValue(activity, KeySet.CONFIGS_VERSION)
            getAdAllData(activity, configsVersion, {
                setAddAdvView(activity, advView, tagData,  onCountDownFinishListener,onAdvClickLisener)
            }, {
                setAddAdvView(activity, advView, tagData,  onCountDownFinishListener,onAdvClickLisener)
            }, {
                setAddAdvView(activity, advView, tagData,  onCountDownFinishListener,onAdvClickLisener)
            })
        }
    }

    fun getStartAdvShow(activity: FragmentActivity,advView: ViewGroup, onCountDownFinishListener: OnCountDownFinishListener?,
                        onAdvClickLisener: AdvClickLisener?){
        getAdvDataAddShow(activity,advView,FIRST_PAGE_START_ADS,onCountDownFinishListener,onAdvClickLisener)
    }

    fun getPOPAdvShow(
        activity: FragmentActivity,
        onAdvClickLisener: AdvClickLisener?
    ) {
        getAdvDataAddShow(activity,null,FIRST_PAGE_POP_ADS,onAdvClickLisener)

    }
    private fun setAddAdvView(
        activity: FragmentActivity, advView: ViewGroup?,
        tagData: String,  onCountDownFinishListener: OnCountDownFinishListener?,onAdvClickLisener: AdvClickLisener?
    ) {
        var onClickLisener: AdvClickLisener
        if(onAdvClickLisener==null){
            onClickLisener =  object:AdvClickLisener{
                override fun onClick(url: String?, title: String?) {
                    activity?.let { it.startActivity(Intent(it, WebPageActivity::class.java).putExtra("weburl",url)) }
                }

                override fun onClick(url: String?, title: String?, hint: String?) {

                }
            }
        }else{
            onClickLisener = onAdvClickLisener
        }


        if (tagData.equals(FIRST_PAGE_CONSULTANT_ADS)
            || tagData.equals(FIRST_PAGE_AGENT_ADS)
            || tagData.equals(RECOMMENDED_AD_COLLECTION)
            || tagData.equals(FIRST_PAGE_AGENCY_ADS)
            || tagData.equals(FIRST_PAGE_FINANCIAL_INSTITUTION_ADS)
        ) {
            advView?.let {view->
                getAdvMenuList(activity, {
                    AdvView().showRecommendAdvData(activity!!,view, activity.supportFragmentManager, it,onClickLisener)
                }, {
                    AdvView().showRecommendAdvData(activity!!,view, activity.supportFragmentManager, null,onClickLisener)
                }, {
                    AdvView().showRecommendAdvData(activity!!,view, activity.supportFragmentManager, null,onClickLisener)
                })
            }

        } else if (tagData.equals(FIRST_PAGE_START_ADS)) {
            advView?.let {
                var elementPlanItems: MutableList<ADPlanItemsData> = ArrayList()
                elementPlanItems.addAll(getAdvList(activity, FIRST_PAGE_START_ADS))
                if (!elementPlanItems.isNullOrEmpty()) {
                    it.visibility = View.VISIBLE

                    AdvView().showStartAdData(activity, it, elementPlanItems, onCountDownFinishListener,onClickLisener)
                } else {
                    it.visibility = View.GONE
                    onCountDownFinishListener?.onCountDownFinish()
                }
            }


        } else if (tagData.equals(FIRST_PAGE_POP_ADS)) {
            var elementPlanItems: MutableList<ADPlanItemsData> = ArrayList()
            elementPlanItems.addAll(getAdvList(activity, FIRST_PAGE_POP_ADS))
            if (!elementPlanItems.isNullOrEmpty()) {
                showAdPopDialog(activity, elementPlanItems,onClickLisener)
            }

        } else {
            advView?.let { addAdvView(activity, it, tagData,onClickLisener) }

        }
    }

    private fun showAdPopDialog(activity: FragmentActivity, popAblest: MutableList<ADPlanItemsData>,onAdvClickLisener: AdvClickLisener?) {
        if (popAblest.size > 0) {

            GlideUtils.preloadImg(activity,popAblest[0].imgUrl,534, 668)
            AdvDialog(activity, popAblest[0].imgUrl, View.OnClickListener {
                if (!isNullOrEmptyStr(popAblest?.first()?.eventLink)) {
                    if (!FastClickUtils.isFastClick()) {

                        onAdvClickLisener?.onClick(popAblest?.first()?.eventLink, "")
                        AdvStatisticsUtils().saveAdvStatisticsdData(activity, AdvStatisticsUtils().changeData(activity, popAblest[0]))

                    }
                }
            }).show()
            var elementPlanItems: MutableList<ADPlanItemsData> = ArrayList()
            elementPlanItems.add(popAblest[0])
            AdvStatisticsUtils().getAdvsdisplayrecord(activity, elementPlanItems)
        }


    }
    var showendAdPopDialog : Boolean = false
     var advDialog :AdvDialog? = null
    private fun showAdPopDialog(activity: FragmentActivity,onAdvClickLisener: AdvClickLisener?)  {
        var popAblest: MutableList<ADPlanItemsData> = ArrayList()
        popAblest.addAll(getAdvList(activity, FIRST_PAGE_POP_ADS))
        if (!popAblest.isNullOrEmpty()) {
            if (popAblest.size > 0) {
                GlideUtils.preloadImg(activity,popAblest[0].imgUrl,534, 668)
                advDialog  = AdvDialog(activity, popAblest[0].imgUrl, View.OnClickListener {
                    if (!isNullOrEmptyStr(popAblest?.first()?.eventLink)) {
                        if (!FastClickUtils.isFastClick()) {
                            onAdvClickLisener?.onClick(popAblest?.first()?.eventLink, "")
//                            AppManager.getAppManager().startWeb(popAblest?.first()?.eventLink, "")
                            AdvStatisticsUtils().saveAdvStatisticsdData(activity, AdvStatisticsUtils().changeData(activity, popAblest[0]))
                            advDialog?.dismiss()
                        }
                    }

                })
                advDialog?.show()
                var elementPlanItems: MutableList<ADPlanItemsData> = ArrayList()
                elementPlanItems.add(popAblest[0])
                AdvStatisticsUtils().getAdvsdisplayrecord(activity, elementPlanItems)
                showendAdPopDialog = true


            }
        }



    }
    /**
     * 首页广告添加
     * tagData：搜索 FIRST_PAGE_SEARCH_ADS、首页中间banner广告 FIRST_PAGE_BANNER_ADS、首页头部banner广告 FIRST_PAGE_TOP_CAROUSEL_ADS、热门信息广告 FIRST_PAGE_BLACK_ADS
    tagStyle :风格
     */
    private fun addAdvView(
        activity: Activity,
        advView: ViewGroup,
        tagData: String,
        tagStyle: String,
        onAdvClickLisener: AdvClickLisener?
    ) {
        if(firstOpen){
            firstOpen = false
            AdvStatisticsUtils().delDisplayRecordData(activity)
        }
        var elementPlanItems: MutableList<ADPlanItemsData> = ArrayList()
        elementPlanItems.addAll(getAdvList(activity, tagData))

        var isRefreshADdata = AdvStatisticsUtils().isRefreshADdata(
            activity,
            elementPlanItems,
            tagData + "DisplayRecord"
        )

        if (isRefreshADdata) {
            when (tagStyle) {
                FIRST_PAGE_SEARCH_ADS -> AdvView().showSearchKeyData(activity,advView, elementPlanItems,onAdvClickLisener)
                FIRST_PAGE_BANNER_ADS -> AdvView().showCentralBannerAdvData(activity,advView, elementPlanItems,onAdvClickLisener)
                FIRST_PAGE_TOP_CAROUSEL_ADS -> AdvView().showTopBannerAdvData(activity,advView, elementPlanItems,onAdvClickLisener)
                FIRST_PAGE_BLACK_ADS -> AdvView().showHotInformationAdvData(activity,advView, elementPlanItems,onAdvClickLisener)

            }
        }
    }

    /**
     * 首页广告添加
     * tagData：搜索 FIRST_PAGE_SEARCH_ADS、首页中间banner广告 FIRST_PAGE_BANNER_ADS、首页头部banner广告 FIRST_PAGE_TOP_CAROUSEL_ADS、热门信息广告 FIRST_PAGE_BLACK_ADS
    tagStyle :风格
     */
    fun addAdvView(
        activity: Activity,
        advView: ViewGroup,
        tagData: String,
        onAdvClickLisener: AdvClickLisener?
    ) {
        if(firstOpen){
            firstOpen = false
            AdvStatisticsUtils().delDisplayRecordData(activity)
        }
        var elementPlanItems: MutableList<ADPlanItemsData> = ArrayList()
        elementPlanItems.addAll(getAdvList(activity, tagData))

        var isRefreshADdata = AdvStatisticsUtils().isRefreshADdata(
            activity,
            elementPlanItems,
            tagData + "DisplayRecord"
        )

        if (isRefreshADdata) {
            when (tagData) {
                FIRST_PAGE_SEARCH_ADS -> AdvView().showSearchKeyData(activity, advView,elementPlanItems,onAdvClickLisener)
                FIRST_PAGE_BANNER_ADS -> AdvView().showCentralBannerAdvData(activity,advView, elementPlanItems,onAdvClickLisener)
                FIRST_PAGE_TOP_CAROUSEL_ADS -> AdvView().showTopBannerAdvData(activity, advView,elementPlanItems,onAdvClickLisener)
                FIRST_PAGE_BLACK_ADS -> AdvView().showHotInformationAdvData(activity,advView, elementPlanItems,onAdvClickLisener)

            }
        }
    }

    fun clearAdvCache(activity: Activity) {
        LocalDataUtils().setValue(activity, KeySet.CONFIGS_VERSION, "1")
        AdvStatisticsUtils().delDisplayRecordData(activity)
        AdvStatisticsUtils().delDisplayAdvCache(activity)
    }

    fun clearAdVersion(activity: Activity) {
        LocalDataUtils().setValue(activity, KeySet.CONFIGS_VERSION, "1")
        AdvStatisticsUtils().delDisplayRecordData(activity)
        AdvStatisticsUtils().delDisplayAdvCache(activity)
    }
    fun ADV_FINISH(){
        LiveDataBus.get().with("COUNTDOWN_ADV_FINISH").postValue("")
    }
}