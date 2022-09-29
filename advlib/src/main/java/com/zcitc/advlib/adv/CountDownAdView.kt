package com.zcitc.advlib.adv

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.zcitc.advlib.R

import com.zcitc.advlib.bean.ADPlanItemsData
import com.zcitc.advlib.utils.setStatusBarHeight
import com.zcitc.glidelibrary.GlideUtils
import com.zcitc.utilslibrary.FastClickUtils
import com.zcitc.utilslibrary.utils.LiveDataBus


/**
 * 倒计时广告view
 */
class CountDownAdView : RelativeLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)

    }
   var onAdvClickLisener: AdvClickLisener? = null
    lateinit var mBinding: View
    lateinit var mToolbarView: View
    lateinit var jumpTv: TextView
    lateinit var nextTv: TextView
    lateinit var mStarAdImg: ImageView
    lateinit var advRL: RelativeLayout
    fun init(context: Context) {
        mBinding = LayoutInflater.from(context).inflate(R.layout.zc_item_countdown_adv, this)
        jumpTv = mBinding.findViewById(R.id.jumpTv)
        mToolbarView = mBinding.findViewById(R.id.mToolbarView)
        mStarAdImg = mBinding.findViewById(R.id.mStarAdImg)
        nextTv = mBinding.findViewById(R.id.nextTv)
        advRL = mBinding.findViewById(R.id.advRL)
       jumpTv.setOnClickListener {
            if (!advUrl.isNullOrEmpty()) {
                if (!FastClickUtils.isFastClick()) {
                    if(!adList.isNullOrEmpty()){
                        AdvStatisticsUtils().saveAdvStatisticsdData(context, AdvStatisticsUtils().changeData(context,adList[0]))
                    }
                    isClickNextStartAd = true
                    onAdvClickLisener?.onClick(advUrl, advTitle)

                }
            }
        }
        nextTv.setOnClickListener {
            mStartAdDownTimer?.cancel()
            onCountDownFinishListener?.onCountDownFinish()
        }

        LiveDataBus.get().with("COUNTDOWN_ADV_FINISH", String::class.java)
            .observeForever {
                if(!isgoAct){
                    isgoAct = true
                    onCountDownFinishListener?.onCountDownFinish()
                }

            }
    }



    private var isgoAct: Boolean = false
    private var advUrl: String? = ""
    private var advTitle: String? = ""
    private var activity: Activity? = null
    var onCountDownFinishListener: OnCountDownFinishListener? = null
    var adList: MutableList<ADPlanItemsData> = ArrayList()
    fun showCountDownAdData(activity: Activity, adList: MutableList<ADPlanItemsData>, onCountDownFinishListener: OnCountDownFinishListener?, onAdvClickLisener: AdvClickLisener?) {
        this.adList = adList
        this.activity = activity
        this.onAdvClickLisener = onAdvClickLisener
        this.onCountDownFinishListener = onCountDownFinishListener
        if (adList.isNullOrEmpty()) {
            return
        }

        if (adList.size > 0) {
            advRL.visibility = View.VISIBLE
            setStatusBarHeight( mToolbarView,activity,activity.windowManager)
            advUrl = adList[0].eventLink
            advTitle = ""

            GlideUtils.showImage(activity, adList[0].imgUrl, mStarAdImg)
            setStartAdDownTimer(adList[0].autoCloseSecondInt, adList[0].autoCloseSecondInt * 1000L)
            mStartAdDownTimer?.start()
            var elementPlanItems: MutableList<ADPlanItemsData> = ArrayList()
            elementPlanItems.add(adList[0])
            AdvStatisticsUtils().getAdvsdisplayrecord(activity, elementPlanItems)
        }


    }


    /**
     * 启动广告倒计时
     */
    var mStartAdDownTimer: CountDownTimer? = null

    /**是否点击跳转链接*/
    var isClickNextStartAd: Boolean = false
    private fun setStartAdDownTimer(countdownTime: Int, millisInFuture: Long) {
        var time = countdownTime
        mStartAdDownTimer = object : CountDownTimer(millisInFuture, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                time--
                if (time == 1) {
                    nextTv.text = "跳过 $time"
                    mStartAdDownTimer?.onFinish()
                } else {
                    nextTv.text = "跳过 $time"
                }


            }

            /**
             * 倒计时结束后调用的
             */
            override fun onFinish() {
                if (!isClickNextStartAd) {
                    isgoAct = true
                    onCountDownFinishListener?.onCountDownFinish()
                }
                mStartAdDownTimer?.cancel()
            }
        }
    }


}