package com.appmarketplace.ozon.presentation.activityes.main_activity


import android.os.*
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.basket_fragment.BasketFragment
import com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.express_fragment.ExpressFragment
import com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.HomeFragment
import com.appmarketplace.ozon.presentation.utils.NetworkConnection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference


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