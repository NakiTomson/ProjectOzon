package com.app.marketPlace.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.marketPlace.R
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.presentation.adapters.BannerAdapter
import kotlinx.android.synthetic.main.activity_onboarding_first_start.*

class FirstStartActivity : AppCompatActivity() {

    private lateinit var onBoardingAdapter:BannerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_first_start)
        setOnBoardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }


    private fun setOnBoardingItems(){
        onBoardingAdapter = BannerAdapter()

//        onBoardingAdapter.setData(
//
//            mutableListOf(
//                Banner(
//                    onBoardingImageUrl = R.drawable.one_card_shopping,
//                    title = "Best Market Place",
//                    description = "Best Market Place"
//                ),
//                Banner(
//                    onBoardingImageUrl = R.drawable.two_board_market,
//                    title = "Best Market Place",
//                    description = "Best Market Place"
//                ),
//                Banner(
//                    onBoardingImageUrl = R.drawable.three_enjoying_time,
//                    title = "Best Market Place",
//                    description = "Best Market Place"
//                )
//            )
//        )


        boarding_viewPager.adapter = onBoardingAdapter


        boarding_viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        (boarding_viewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        imageNextMoxyData.setOnClickListener {
            if (boarding_viewPager.currentItem + 1 < onBoardingAdapter.itemCount){
                boarding_viewPager.currentItem += 1
            }else{
                navigateToHomeActivity()
            }
        }

        text_skip.setOnClickListener { navigateToHomeActivity() }
        buttonGetStarted.setOnClickListener { navigateToHomeActivity() }
    }

    private fun setupIndicators(){

        val indicators = arrayOfNulls<ImageView>(onBoardingAdapter.itemCount)

        val layoutParams:LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for ( i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicators_container.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int){
        val childCount  = indicators_container.childCount
        for (i in 0 until  childCount){
            val imageView = indicators_container.getChildAt(i) as ImageView
            if (i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }

    private fun navigateToHomeActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = hideSystemBar()
        }
    }

    private fun hideSystemBar(): Int {
        return (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
}