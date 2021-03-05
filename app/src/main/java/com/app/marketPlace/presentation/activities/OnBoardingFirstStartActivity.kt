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
import com.app.marketPlace.presentation.adapters.BannerAdapter
import com.app.marketPlace.domain.modelsUI.OnBoardingItem
import kotlinx.android.synthetic.main.activity_onboarding_first_start.*

class OnBoardingFirstStartActivity : AppCompatActivity() {

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

        onBoardingAdapter.setData(

            mutableListOf(
                OnBoardingItem(
                    onBoardingImage = R.drawable.one_card_shopping,
                    title = "Best Market Place",
                    description = "Best Market Place"
                ),
                OnBoardingItem(
                    onBoardingImage = R.drawable.two_board_market,
                    title = "Best Market Place",
                    description = "Best Market Place"
                ),
                OnBoardingItem(
                    onBoardingImage = R.drawable.three_enjoying_time,
                    title = "Best Market Place",
                    description = "Best Market Place"
                )
            )
        )


        onBoardingViewPager.adapter = onBoardingAdapter


        onBoardingViewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        (onBoardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        imageNext.setOnClickListener {
            if (onBoardingViewPager.currentItem + 1 < onBoardingAdapter.itemCount){
                onBoardingViewPager.currentItem += 1
            }else{
                navigateToHomeActivity()
            }
        }

        textSkip.setOnClickListener { navigateToHomeActivity() }
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
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int){
        val childCount  = indicatorsContainer.childCount
        for (i in 0 until  childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
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