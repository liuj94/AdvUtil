package com.zcitc.advlib.fragment

import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_AGENCY_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_AGENT_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_CONSULTANT_ADS
import com.zcitc.advlib.KeySet.Companion.FIRST_PAGE_FINANCIAL_INSTITUTION_ADS
import com.zcitc.advlib.R
import com.zcitc.advlib.adapter.RecommendAdapter
import com.zcitc.advlib.adv.AdvStatisticsUtils
import com.zcitc.advlib.adv.getAdvList
import com.zcitc.advlib.bean.ADPlanItemsData
import com.zcitc.utilslibrary.utils.LiveDataBus

import java.util.*

/**
 *   author : LiuJie
 *   date   : 2021/3/114:33
 */
class RecommendFragment : BaseFragment() {

        companion object {
        fun newInstance(type: String): RecommendFragment {
            val args = Bundle()
            args.putString("type", type)
            val fragment = RecommendFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override val layoutId: Int = R.layout.zc_fragment_home_recommend

        var type: String = ""
    var dataList: MutableList<ADPlanItemsData> = ArrayList()
    var adapter: RecommendAdapter? = null
   lateinit var recommendRecyclerView : RecyclerView
    override fun initView(view: View?, savedInstanceState: Bundle?)
    {
        view?.let {
            recommendRecyclerView = it.findViewById(R.id.recommendRecyclerView)
        }

    }

    override fun initData() {
        type = requireArguments().getString("type","")
        dataList.clear()
        dataList.addAll(getAdvList(requireActivity(),type))

       recommendRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 3)

        if (type.equals(FIRST_PAGE_CONSULTANT_ADS)) {
            //优秀顾问,
            adapter = RecommendAdapter(activity,dataList, R.layout.zc_item_recommend, 11)

        } else if (type.equals(FIRST_PAGE_AGENT_ADS)) {
            //优秀中介
            adapter = RecommendAdapter(activity,dataList, R.layout.zc_item_recommend, 21)
        } else if (type.equals(FIRST_PAGE_AGENCY_ADS)) {
            //优秀门店
            adapter = RecommendAdapter(activity,dataList, R.layout.zc_item_recommend2, 1)
        } else if (type.equals(FIRST_PAGE_FINANCIAL_INSTITUTION_ADS)) {
            if(dataList.size>0){
                if (dataList[0].contentType.equals("1")) {
                    for (it in dataList) {
                        for (it2 in it.texts) {
                            if (it2.code == "title") {
                                it.name = it2.content
                            }
                            if (it2.code == "subTitle") {
                                it.content = it2.content
                            }
                        }


                    }
                }
            }

            //金融机构
            adapter = RecommendAdapter(activity,dataList, R.layout.zc_item_recommend3, 31)
        }else{
            adapter = RecommendAdapter(activity,dataList, R.layout.zc_item_recommend3, 31)
        }

        recommendRecyclerView.adapter = adapter
        val emptyView: View = layoutInflater.inflate(R.layout.zc_item_empty_layout3, null)
        adapter?.setEmptyView(emptyView)

        adapter?.setOnItemClickListener { _, _, position ->
            try {
                AdvStatisticsUtils().saveAdvStatisticsdData(requireActivity(),AdvStatisticsUtils().changeData(requireActivity(), dataList[position]))
                if (dataList[position] != null) {
                    val msg = Message()
                    msg.obj = dataList[position].eventLink
                    msg.what = 100

                    LiveDataBus.get().with("RECOMMEND_ADV_CLICK").postValue(msg)
//                    AppManager.getAppManager().startWeb(dataList[position].eventLink, "")
                }
            }catch (e: Exception) {}


        }
        if (!dataList.isNullOrEmpty()) {
            activity?.let {
                AdvStatisticsUtils().saveDisplayRecordData(requireActivity(),dataList,type+"DisplayRecord")

            }
        }
    }
}

