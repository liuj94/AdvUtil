package com.zcitc.advlib.adv

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import com.youth.banner.config.BannerConfig
import com.youth.banner.listener.OnBannerListener
import com.zcitc.advertisement.adv.InformationAdvView
import com.zcitc.advlib.ApiRoutes
import com.zcitc.advlib.R
import com.zcitc.advlib.bean.ADPlanItemsData
import com.zcitc.advlib.bean.AdvertisementRecommendationData
import com.zcitc.advlib.utils.dp2px
import com.zcitc.advlib.widget.AutoScrollTextView
import com.zcitc.glidelibrary.GlideUtils
import com.zcitc.utilslibrary.FastClickUtils


class AdvView  {


    /**
     * 启动页广告
     *gotoUrl:外部打app要跳转的链接
     */
//    fun showStartAdData(
//        activity: Activity,
//        rootbinding: ViewGroup,
//        startAdData: MutableList<ADPlanItemsData>,
//        onCountDownFinishListener: CountDownAdView.OnCountDownFinishListener?,
//        onAdvClickLisener: AdvClickLisener?
//    ) {
//        rootbinding.removeAllViews()
//        var countDownAdView: CountDownAdView = CountDownAdView(activity)
//        countDownAdView.showCountDownAdData(activity, startAdData,onCountDownFinishListener,onAdvClickLisener)
//        rootbinding.addView(countDownAdView)
//    }



    /**
     * 顶部搜索关键字广告
     *  item_adv_search_key
     */
    fun showSearchKeyData(activity: Context,  rootbinding: ViewGroup,list: List<ADPlanItemsData>?, onAdvClickLisener: AdvClickLisener?) {

        rootbinding.removeAllViews()

        var toolbarKey = AutoScrollTextView(activity)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        toolbarKey.layoutParams = lp
        toolbarKey.setPadding(0, dp2px(activity, 9), 0, 0)
        var hotSearchesKeyList: MutableList<String> = ArrayList()
        if (list.isNullOrEmpty()) {
            setNullSearchsDataView(hotSearchesKeyList, toolbarKey,onAdvClickLisener)
            rootbinding.addView(toolbarKey)
            return
        }
        try {

            for (dataList in list) {
                if (dataList.bizData != null) {
                    if (!dataList.bizData.name.isNullOrEmpty()) {
                        hotSearchesKeyList.add(dataList.bizData.name!!)
                    }

                }
            }

            if (!hotSearchesKeyList.isNullOrEmpty()) {
                toolbarKey.setText(hotSearchesKeyList[0])
                toolbarKey.setList(hotSearchesKeyList)
                toolbarKey.stopScroll()
                toolbarKey.startScroll()
                toolbarKey.setClickLisener {
                    try {
                        AdvStatisticsUtils().saveAdvStatisticsdData(
                            activity,
                            AdvStatisticsUtils().changeData(
                                activity,
                                list[it]
                            )
                        )
                        onAdvClickLisener?.onClick(ApiRoutes.HOUSESEARCH,"搜索", hotSearchesKeyList[it])
//                        AppManager.getAppManager()
//                            .startWeb(ApiRoutes.HOUSESEARCH, false, "搜索", hotSearchesKeyList[it])
                    } catch (e: Exception) {
                    }

                }
            } else {
                setNullSearchsDataView(hotSearchesKeyList, toolbarKey,onAdvClickLisener)
            }
        } catch (e: Exception) {
            setNullSearchsDataView(hotSearchesKeyList, toolbarKey,onAdvClickLisener)

        }
        rootbinding.addView(toolbarKey)

    }

    private fun setNullSearchsDataView(
        hotSearchesKeyList: MutableList<String>,
        toolbarKey: AutoScrollTextView,
        onAdvClickLisener: AdvClickLisener?
    ) {
        hotSearchesKeyList.clear()
        hotSearchesKeyList.add("请输入房源名称")
        toolbarKey.setText(hotSearchesKeyList[0])
        toolbarKey.setList(hotSearchesKeyList)
        toolbarKey.stopScroll()
        toolbarKey.setClickLisener {
            onAdvClickLisener?.onClick(ApiRoutes.HOUSESEARCH, "搜索", "")
//            AppManager.getAppManager().startWeb(ApiRoutes.HOUSESEARCH, false, "搜索", "")
        }
    }

//    /**
//     * 顶部轮播图广告
//     * item_adv_top_banner
//     * 984*400
//     */
//
//    fun showTopBannerAdvData(activity: Activity, rootbinding: ViewGroup, bannerList: List<ADPlanItemsData>?,onAdvClickLisener : AdvClickLisener?) {
//        rootbinding.removeAllViews()
//        var topBanner: MyBanner =
//            createBanner(activity, 0.4065040650406504f, BannerConfig.CIRCLE_INDICATOR, 0)
//        setBannerData(
//            activity,
//            topBanner,
//            bannerList,
//            onAdvClickLisener
//        )
//        rootbinding.addView(topBanner)
//    }
//
//    fun createBanner(activity: Activity, proportion: Float, style: Int, topH: Int): MyBanner {
//        var banner: MyBanner = MyBanner(activity)
//        val display: Display = activity.windowManager.defaultDisplay
//        val lp = LinearLayout.LayoutParams(
//            display.width, Math.round(display.width * proportion).toInt()
//        )
//        lp.topMargin = dp2px(activity, topH)
//        banner.layoutParams = lp
//        banner.setBackgroundColor(activity.resources.getColor(R.color.gray_f5f5f5))
//        banner.setPadding(dp2px(activity, 16), 0, dp2px(activity, 16), 0)
//        banner.setBannerStyle(style)
//        return banner
//    }
//
//    private fun setBannerData(
//        activity: Activity,
//        banner: MyBanner,
//        bannerList: List<ADPlanItemsData>?,
//        onAdvClickLisener: AdvClickLisener?
//    ) {
//
//        var path: List<*>? = null
//        if (bannerList.isNullOrEmpty()) {
//            path = java.util.ArrayList<Int>()
//            path.add(com.zcitc.glidelibrary.R.mipmap.banner)
//        } else {
//            path = java.util.ArrayList<String>()
//            for (bean in bannerList) {
//                path.add(bean.getImgUrl())
//            }
//        }
//
//        banner.setImageLoader(GlideImageLoader())
//        banner.setImages(path)
//        banner.setDelayTime(3000)
//        banner.setViewPagerIsScroll(true)
//        banner.start()
//        bannerList?.let {
//            if (bannerList.size > 1) {
//                banner.setOnBannerListener(OnBannerListener { position ->
//                    if (!bannerList.isNullOrEmpty()) {
//                        if (position < bannerList.size) {
//                            if (!bannerList[position].eventLink.isNullOrEmpty()
//                            ) {
//                                if (!FastClickUtils.isFastClick()) {
//                                    onAdvClickLisener?.onClick(bannerList[position].eventLink, "")
////                                    AppManager.getAppManager()
////                                        .startWeb(bannerList[position].eventLink, "")
//                                    AdvStatisticsUtils().saveAdvStatisticsdData(
//                                        activity,
//                                        AdvStatisticsUtils().changeData(
//                                            activity,
//                                            bannerList[position]
//                                        )
//                                    )
//                                }
//                            }
//                        }
//                    }
//                })
//            } else if (bannerList.size == 1) {
//                banner.setOnClickListener(OnClickListener { v: View? ->
//                    if (bannerList.size > 0) {
//                        if (bannerList.get(0).eventLink != null && "" != bannerList.get(0)
//                                .eventLink
//                        ) {
//                            if (!FastClickUtils.isFastClick()) {
//                                onAdvClickLisener?.onClick(bannerList.get(0).eventLink, "")
////                                AppManager.getAppManager().startWeb(bannerList.get(0).eventLink, "")
//                                AdvStatisticsUtils().saveAdvStatisticsdData(
//                                    activity,
//                                    AdvStatisticsUtils().changeData(
//                                        activity,
//                                        bannerList.get(0)
//                                    )
//                                )
//                            }
//                        }
//                    }
//
//
//                })
//            } else {
//
//            }
//        }
//
//    }
//
//    /**
//     * 中部轮播图广告
//     * item_adv_top_banner
//     */
//    fun showCentralBannerAdvData(activity: Activity, bannerList: List<ADPlanItemsData>?, onAdvClickLisener: AdvClickLisener?) {
//        rootbinding.advRootRl.removeAllViews()
//        rootbinding.advRootRl.visibility = View.GONE
//        if (!bannerList.isNullOrEmpty()) {
//            rootbinding.advRootRl.visibility = View.VISIBLE
//            var banner: MyBanner =
//                createBanner(activity, 0.19271623672f, BannerConfig.NOT_INDICATOR, 10)
//            setBannerData(
//                activity,
//                banner,
//                bannerList,
//                onAdvClickLisener
//            )
//            rootbinding.advRootRl.addView(banner)
//        }
//
//
//    }

    /**
     *推荐广告  如：优秀门店, 优秀顾问，优秀中介
     * item_adv_recommend
     */

    fun showRecommendAdvData(
        activity: Activity,
        rootbinding: ViewGroup,
        childFragmentManager: FragmentManager,
        data: AdvertisementRecommendationData?,
        onAdvClickLisener: AdvClickLisener?
    ) {
        rootbinding.removeAllViews()
        rootbinding.visibility = View.GONE
        if (data != null) {
            var recommendAdvView: RecommendAdvView = RecommendAdvView(activity)
            setTopMargin(recommendAdvView, activity)
            recommendAdvView.showRecommendAdvData(activity, childFragmentManager, data,onAdvClickLisener)
            rootbinding.addView(recommendAdvView)
            rootbinding.visibility = View.VISIBLE
        }
    }

    /**
     * 推荐文章 资讯广告
     *item_adv_news
     */

    fun showHotInformationAdvData(activity: Activity, rootbinding: ViewGroup, data: MutableList<ADPlanItemsData>,onAdvClickLisener: AdvClickLisener?) {
        rootbinding.removeAllViews()
        rootbinding.visibility = View.GONE
        if (!data.isNullOrEmpty()) {
            rootbinding.visibility = View.VISIBLE
            var informationAdvView: InformationAdvView = InformationAdvView(activity)
            setTopMargin(informationAdvView, activity)
            informationAdvView.showInformationAdvData(activity, data,onAdvClickLisener)
            rootbinding.addView(informationAdvView)
        }


    }

    fun setTopMargin(view: View, activity: Activity) {
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.topMargin = dp2px(activity, 10)
        view.layoutParams = lp
    }

//    class GlideImageLoader : ImageLoader() {
//        override fun displayImage(context: Context?, path: Any?, imageView: ImageView) {
//            try {
//                if (path is String) {
//                    GlideUtils.showImage(
//                        context,
//                        path as String?,
//                        imageView,
//                        com.zcitc.glidelibrary.R.mipmap.home_image_placeholder3
//                    )
//                    imageView.scaleType = ImageView.ScaleType.FIT_XY
//                }
//                if (path is Int) {
//                    GlideUtils.showImage(
//                        context,
//                        com.zcitc.glidelibrary.R.mipmap.banner,
//                        imageView,
//                        com.zcitc.glidelibrary.R.mipmap.home_image_placeholder3
//                    )
//                    imageView.scaleType = ImageView.ScaleType.FIT_XY
//
//                }
//            } catch (e: java.lang.Exception) {
//                e.printStackTrace()
//            }
//        }
//    }


}