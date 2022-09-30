package com.zcitc.advlib.adv

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.zcitc.advlib.R
import com.zcitc.advlib.adapter.FragmentStatePagerItemAdapter
import com.zcitc.advlib.adapter.MyCommonNavigatorAdapter
import com.zcitc.advlib.bean.ADPlanItemsData
import com.zcitc.advlib.bean.AdvertisementRecommendationData
import com.zcitc.advlib.fragment.RecommendFragment
import com.zcitc.advlib.widget.MyViewPager
import com.zcitc.utilslibrary.utils.LiveDataBus
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

class RecommendAdvView : RelativeLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)

    }

    lateinit var itemAdvRecommendBinding: View
    lateinit var recommendLl: LinearLayout
    lateinit var recommendVp: MyViewPager
    lateinit var recommendMagicIndicator: MagicIndicator
    fun init(context: Context) {
        itemAdvRecommendBinding = LayoutInflater.from(context).inflate(R.layout.zc_item_adv_recommend, this)
        recommendLl = itemAdvRecommendBinding.findViewById(R.id.recommendLl)
        recommendVp = itemAdvRecommendBinding.findViewById(R.id.recommendVp)
        recommendMagicIndicator = itemAdvRecommendBinding.findViewById(R.id.recommendMagicIndicator)
        LiveDataBus.get().with("RECOMMEND_ADV_CLICK", Message::class.java)
            .observeForever {
                onAdvClickLisener?.onClick(it.obj as String, "")
            }
    }

    var recommendTabPagerAdapter: FragmentStatePagerItemAdapter? = null
    var onAdvClickLisener: AdvClickLisener? = null
    fun showRecommendAdvData(
        activity: Activity,
        childFragmentManager: FragmentManager,
        data: AdvertisementRecommendationData,
        onAdvClickLisener: AdvClickLisener?
    ) {
        this.onAdvClickLisener = onAdvClickLisener
        recommendLl?.visibility = View.GONE
        if (recommendTabPagerAdapter == null) {
            var fragments: MutableList<Fragment> = ArrayList()
            var titles: MutableList<String> = ArrayList()
            for (it in data.datas) {
                var dataList: MutableList<ADPlanItemsData> = java.util.ArrayList()
                dataList.addAll(getAdvList(activity, it.codeStr))
                if (dataList.size > 0) {
                    titles.add(it.tags)
                    val fragment =
                        RecommendFragment.newInstance(it.codeStr)
                    fragments.add(fragment)
                }

            }

            if (fragments.size > 0) {
                if (recommendTabPagerAdapter == null) {
                    recommendTabPagerAdapter =
                        FragmentStatePagerItemAdapter(childFragmentManager, fragments)
                    recommendVp?.adapter = recommendTabPagerAdapter

                    recommendMagicIndicator?.setBackgroundColor(Color.TRANSPARENT)

                    val commonNavigator = CommonNavigator(activity)
                    commonNavigator.isAdjustMode = titles.size <= 4
                    commonNavigator.adapter =
                        MyCommonNavigatorAdapter(
                            activity,
                            titles,
                            recommendVp
                        )
                    recommendMagicIndicator.navigator = commonNavigator
                    ViewPagerHelper.bind(
                        recommendMagicIndicator,
                        recommendVp
                    )
                    if (fragments.size > 0) {
                        recommendLl?.visibility = View.VISIBLE
                    } else {
                        recommendLl?.visibility = View.GONE
                    }
                } else {
                    recommendTabPagerAdapter?.setDataSource(fragments)
                    recommendTabPagerAdapter?.notifyDataSetChanged()
                    if (fragments.size > 0) {
                        recommendLl?.visibility = View.VISIBLE
                    } else {
                        recommendLl?.visibility = View.GONE
                    }
                }
            }
        }
    }
}