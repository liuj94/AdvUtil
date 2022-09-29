package com.zcitc.advlib.adv;

public interface AdvClickLisener {
    /**
     *
     * @param url 链接
     * @param title 标题
     */
    void onClick(String url,String title);

    /**
     *
     *@param url 链接
     *@param title 标题
     * @param hint 默认搜索显示值 只限于搜索广告
     */
    void onClick(String url,String title,String hint);
}
