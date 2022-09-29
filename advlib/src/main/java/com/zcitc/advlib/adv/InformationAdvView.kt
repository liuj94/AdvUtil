package com.zcitc.advertisement.adv

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zcitc.advlib.R
import com.zcitc.advlib.adapter.PlateRecommendAdapter
import com.zcitc.advlib.adv.AdvClickLisener
import com.zcitc.advlib.bean.ADPlanItemsData


class InformationAdvView : RelativeLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)

    }
    lateinit var mBinding: View
    lateinit var recommendPlateRv: RecyclerView
    fun init(context: Context) {
        mBinding = LayoutInflater.from(context).inflate(R.layout.zc_item_adv_news, this)
//        mBinding = ItemAdvNewsBinding.inflate(LayoutInflater.from(context), this, true)
        recommendPlateRv = mBinding.findViewById(R.id.recommendPlateRv)

    }
    fun showInformationAdvData(activity: Activity, adList: MutableList<ADPlanItemsData>, onAdvClickLisener: AdvClickLisener?) {
            var mRecommendArticleAdapter: PlateRecommendAdapter
            recommendPlateRv.layoutManager = GridLayoutManager(activity, 2)
            mRecommendArticleAdapter = PlateRecommendAdapter(adList)
            recommendPlateRv.adapter = mRecommendArticleAdapter
            mRecommendArticleAdapter?.setOnItemClickListener { _, _, position ->
                if (position < adList.size)
                    onAdvClickLisener?.onClick(adList[position].eventLink, "")
            }


    }
}