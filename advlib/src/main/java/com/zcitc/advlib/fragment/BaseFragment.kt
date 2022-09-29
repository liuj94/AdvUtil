package com.zcitc.advlib.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {
    protected var mActivity: Activity? = null

    /**
     * 获得全局的，防止使用getActivity()为空
     * @param context
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity?
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = LayoutInflater.from(mActivity)
            .inflate(layoutId, container, false)
        initView(view, savedInstanceState)
        return view
    }


    override  fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     * @return
     */
    protected abstract val layoutId: Int

    /**
     * 该抽象方法就是 初始化view
     * @param view
     * @param savedInstanceState
     */
    protected abstract fun initView(view: View?, savedInstanceState: Bundle?)

    /**
     * 执行数据的加载
     */
    protected abstract fun initData()
}
