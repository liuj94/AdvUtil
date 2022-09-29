package com.zcitc.zcitadvdome

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.zcitc.advlib.KeySet
import com.zcitc.advlib.adv.AdvClickLisener
import com.zcitc.advlib.adv.AdvViewUtil
lateinit var layout1: RelativeLayout
lateinit var layout2: RelativeLayout
lateinit var layout3: RelativeLayout

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         layout1 = findViewById(R.id.layout1)
         layout2 = findViewById(R.id.layout2)
         layout3 = findViewById(R.id.layout3)

        AdvViewUtil().getAdvDataAddShow(this,layout3, KeySet.FIRST_PAGE_CONSULTANT_ADS,object : AdvClickLisener{
            override fun onClick(url: String?, title: String?) {

            }

            override fun onClick(url: String?, title: String?, hint: String?) {

            }

        })

        AdvViewUtil().getAdvDataAddShow(this,layout2, KeySet.FIRST_PAGE_SEARCH_ADS,object : AdvClickLisener{
            override fun onClick(url: String?, title: String?) {

            }

            override fun onClick(url: String?, title: String?, hint: String?) {

            }

        })

        AdvViewUtil().getAdvDataAddShow(this,layout1, KeySet.FIRST_PAGE_BLACK_ADS,object : AdvClickLisener{
            override fun onClick(url: String?, title: String?) {

            }

            override fun onClick(url: String?, title: String?, hint: String?) {

            }

        })

//       var bannerList =  ArrayList<String>();
//        bannerList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
//        bannerList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
//        bannerList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
//        bannerList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
//
//      var adapter =  object: BannerImageAdapter<String>(bannerList){
//            override fun onBindView(holder: BannerImageHolder, data: String, position: Int, size: Int) {
//                Glide.with(holder.itemView)
//                    .load(data)
//                    .apply(RequestOptions.bitmapTransform( RoundedCorners(30)))
//                    .into(holder.imageView);
//            }
//
//        }
//        var banner :Banner<String,BannerImageAdapter<String>> = findViewById<Banner<String,BannerImageAdapter<String>>>(R.id.banner);
//        banner.setAdapter(adapter)
//            .addBannerLifecycleObserver(this)
//            .setIndicatorSelectedColor(Color.BLUE)
//            .setIndicatorNormalColor(Color.BLACK)
//            .indicator = RoundLinesIndicator(this)


//        AdvViewUtil().getAdvDataAddShow()
    }

    fun nextClick(view: View) {
        AdvViewUtil().getAdvDataAddShow(this,layout1, KeySet.FIRST_PAGE_BLACK_ADS,object : AdvClickLisener{
            override fun onClick(url: String?, title: String?) {

            }

            override fun onClick(url: String?, title: String?, hint: String?) {

            }

        })
    }
}