package com.appmarketplace.ozon.presentation.activityes.main_activity


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.basket_fragment.BasketFragment
import com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.express_fragment.ExpressFragment
import com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.HomeFragment
import com.appmarketplace.ozon.presentation.utils.NetworkConnection
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {



    private val homeFragment = HomeFragment()
    private val expressFragment = ExpressFragment()
    private val basketFragment = BasketFragment()

    private var activeFragment:Fragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction().apply {
            add(R.id.containerFrame, homeFragment, "home").show(homeFragment)
            add(R.id.containerFrame, expressFragment, "express").hide(expressFragment)
            add(R.id.containerFrame, basketFragment, "basket").hide(basketFragment)
        }.commit()

        initListeners()
        setOtherSetting()
    }


    private fun setOtherSetting(){
        bottomNavigationView.itemIconTintList = null
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            this.window.statusBarColor = Color.WHITE
//        }
    }

    private fun initListeners() {
        activeFragment = homeFragment
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeItem -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(homeFragment).commit()
                    activeFragment = homeFragment
                    true
                }

                R.id.expressItem -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(
                        expressFragment
                    ).commit()
                    activeFragment = expressFragment
                    true
                }
                R.id.basketItem -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(
                        basketFragment
                    ).commit()
                    activeFragment = basketFragment
                    true
                }
                else -> false
            }
        }
    }

    fun cheakInternetConnection(){
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected) {
                Log.v("TAGIS", "IS $isConnected")
            } else {
                Log.v("TAGIS", "IS $isConnected")
            }
        })
    }


}