package com.zcitc.advlib.adv

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.zcitc.advertisement.adv.InformationAdvView
import com.zcitc.advlib.ApiRoutes
import com.zcitc.advlib.bean.ADPlanItemsData
import com.zcitc.advlib.bean.AdvertisementRecommendationData
import com.zcitc.advlib.utils.dp2px
import com.zcitc.advlib.widget.AutoScrollTextView
import com.zcitc.glidelibrary.GlideUtils


class AdvView {


    /**
     * 启动页广告
     *gotoUrl:外部打app要跳转的链接
     */
    fun showStartAdData(
        activity: Activity,
        rootbinding: ViewGroup,
        startAdData: MutableList<ADPlanItemsData>,
        onCountDownFinishListener: OnCountDownFinishListener?,
        onAdvClickLisener: AdvClickLisener?
    ) {
        rootbinding.removeAllViews()
        var countDownAdView = CountDownAdView(activity)
        countDownAdView.showCountDownAdData(activity, startAdData,onCountDownFinishListener,onAdvClickLisener)
        rootbinding.addView(countDownAdView)
    }


    /**
     * 顶部搜索关键字广告
     *  item_adv_search_key
     */
    fun showSearchKeyData(activity: Context, rootbinding: ViewGroup, list: List<ADPlanItemsData>?, onAdvClickLisener: AdvClickLisener?) {

        rootbinding.removeAllViews()

        var toolbarKey = AutoScrollTextView(activity)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        toolbarKey.layoutParams = lp
        toolbarKey.setPadding(0, dp2px(activity, 9), 0, 0)
        var hotSearchesKeyList: MutableList<String> = ArrayList()
        if (list.isNullOrEmpty()) {
            setNullSearchsDataView(hotSearchesKeyList, toolbarKey, onAdvClickLisener)
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
                        onAdvClickLisener?.onClick(ApiRoutes.HOUSESEARCH, "搜索", hotSearchesKeyList[it])
                    } catch (e: Exception) {
                    }

                }
            } else {
                setNullSearchsDataView(hotSearchesKeyList, toolbarKey, onAdvClickLisener)
            }
        } catch (e: Exception) {
            setNullSearchsDataView(hotSearchesKeyList, toolbarKey, onAdvClickLisener)

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

    /**
     * 顶部轮播图广告
     * item_adv_top_banner
     * 984*400
     */

    fun showTopBannerAdvData(activity: Activity, advRootRl: ViewGroup, bannerList: List<ADPlanItemsData>?, onAdvClickLisener: AdvClickLisener?) {
        advRootRl.removeAllViews()
        var banner = createBanner(activity, 0.4065040650406504f)
        banner.setAdapter(getBannerImageAdapter(activity, bannerList))
        banner.indicator = CircleIndicator(activity)
        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER)
        banner.setIndicatorSelectedColor(Color.BLACK)
        banner.setIndicatorNormalColor(Color.GRAY)
        banner.setOnBannerListener { _, position ->
            bannerList?.let {
                if (it.size > 0 && position < it.size) {
                    if (it[position].eventLink != null && "" != it[position].eventLink
                    ) {
                        onAdvClickLisener?.onClick(it[position].eventLink, "")
                        AdvStatisticsUtils().saveAdvStatisticsdData(activity, AdvStatisticsUtils().changeData(activity, it[position]))
                    }
                }
            }

        }
        advRootRl.addView(banner)
    }

    fun createBanner(activity: Activity, proportion: Float): Banner<Any, BannerImageAdapter<Any>> {
        var banner: Banner<Any, BannerImageAdapter<Any>> = Banner<Any, BannerImageAdapter<Any>>(activity)
        val display: Display = activity.windowManager.defaultDisplay
        val lp = LinearLayout.LayoutParams(
            display.width, Math.round(display.width * proportion).toInt()
        )
        banner.layoutParams = lp
        banner.setBannerRound(15f)
        return banner
    }


    /**
     * 中部轮播图广告
     * item_adv_top_banner
     */
    fun showCentralBannerAdvData(activity: Activity, advRootRl: ViewGroup, bannerList: List<ADPlanItemsData>?, onAdvClickLisener: AdvClickLisener?) {
        advRootRl.removeAllViews()
        advRootRl.visibility = View.GONE
        if (!bannerList.isNullOrEmpty()) {
            advRootRl.visibility = View.VISIBLE


            var banner = createBanner(activity, 0.19271623672f)
            banner.setAdapter(getBannerImageAdapter(activity, bannerList))
            banner.setOnBannerListener { _, position ->
                if (bannerList.size > 0 && position < bannerList.size) {
                    if (bannerList[position].eventLink != null && "" != bannerList[position].eventLink
                    ) {
                        onAdvClickLisener?.onClick(bannerList[position].eventLink, "")
                        AdvStatisticsUtils().saveAdvStatisticsdData(activity, AdvStatisticsUtils().changeData(activity, bannerList[position]))
                    }
                }
            }
            advRootRl.addView(banner)
        }


    }

    fun getBannerImageAdapter(activity: Activity, bannerList: List<ADPlanItemsData>?): BannerImageAdapter<Any> {

        var path: List<Any>? = null
        if (bannerList.isNullOrEmpty()) {
            path = java.util.ArrayList<Int>()
            path.add(com.zcitc.glidelibrary.R.mipmap.banner)
        } else {
            path = java.util.ArrayList<String>()
            for (bean in bannerList) {
                path.add(bean.getImgUrl())
            }
        }
        var adapter = object : BannerImageAdapter<Any>(path) {
            override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerImageHolder {
                val imageView = ImageView(parent!!.context)
                val params = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                imageView.layoutParams = params
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                return BannerImageHolder(imageView)

            }
            override fun onBindView(holder: BannerImageHolder, data: Any, position: Int, size: Int) {
                if (data is String) {
                    GlideUtils.showImage(
                        activity,
                        data as String?,
                        holder.imageView,
                        com.zcitc.glidelibrary.R.mipmap.home_image_placeholder3
                    )


                } else if (data is Int) {
                    GlideUtils.showImage(
                        activity,
                        com.zcitc.glidelibrary.R.mipmap.banner,
                        holder.imageView,
                        com.zcitc.glidelibrary.R.mipmap.home_image_placeholder3
                    )


                }
            }

        }
        return adapter
    }


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
            recommendAdvView.showRecommendAdvData(activity, childFragmentManager, data, onAdvClickLisener)
            rootbinding.addView(recommendAdvView)
            rootbinding.visibility = View.VISIBLE
        }
    }

    /**
     * 推荐文章 资讯广告
     *item_adv_news
     */

    fun showHotInformationAdvData(activity: Activity, rootbinding: ViewGroup, data: MutableList<ADPlanItemsData>, onAdvClickLisener: AdvClickLisener?) {
        rootbinding.removeAllViews()
        rootbinding.visibility = View.GONE
        if (!data.isNullOrEmpty()) {
            rootbinding.visibility = View.VISIBLE
            var informationAdvView: InformationAdvView = InformationAdvView(activity)
            setTopMargin(informationAdvView, activity)
            informationAdvView.showInformationAdvData(activity, data, onAdvClickLisener)
            rootbinding.addView(informationAdvView)
        }


    }

    fun setTopMargin(view: View, activity: Activity) {
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.topMargin = dp2px(activity, 10)
        view.layoutParams = lp
    }



}