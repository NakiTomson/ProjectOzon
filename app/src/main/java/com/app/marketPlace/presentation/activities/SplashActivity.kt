package com.app.marketPlace.presentation.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.app.marketPlace.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val sharedPreferences = getSharedPreferences("MARKET",Context.MODE_PRIVATE)
        startMainAnimation(sharedPreferences)
        startGlobalService()
//        private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    }

    private fun startMainAnimation(sharedPreferences: SharedPreferences) {
        val animation: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.main_scale
        )

        animation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                when {
                    sharedPreferences.getBoolean("FirstOpen", true) -> {
//                        startOnBoardingActivity()
                        startMainActivity()
                        sharedPreferences.edit().putBoolean("FirstOpen", false).apply()
                    }
                    else -> {
                        startMainActivity()
                    }
                }

            }
        })
        mainImageView.animation = animation
    }


    private fun startGlobalService(){

    }


    fun startMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun startOnBoardingActivity(){
        startActivity(Intent(this, OnBoardingFirstStartActivity::class.java))
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