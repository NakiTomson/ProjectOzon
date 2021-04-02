package com.app.marketPlace.presentation.activities


import android.os.*
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.app.marketPlace.R
import com.app.marketPlace.presentation.extensions.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        mainViewModel.allIdsInBaskets.observe(this, { list->
            list?.let {
                MainViewModel.listIdsBasket.clear()
                MainViewModel.listIdsBasket.addAll(it)
            }
        })

        mainViewModel.allIdsInFavorite.observe(this, { list->
            list?.let {
                MainViewModel.listIdsFavorite.clear()
                MainViewModel.listIdsFavorite.addAll(it)
            }
        })
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }


    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navGraphIds = listOf(R.navigation.home, R.navigation.category, R.navigation.basket,R.navigation.favorite,R.navigation.account)
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller
        checkInternetConnection()
        bottomNavigationView.callOnClick()
    }


    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun checkInternetConnection() {
        mainViewModel.networkConnection.observe(this,{isConnected->
            if (isConnected) {
                connection_frame.visibility = View.GONE
                bottomNavigationView.visibility = View.VISIBLE
            } else {
                connection_frame.visibility = View.VISIBLE
                bottomNavigationView.visibility = View.GONE
            }
        })
    }
}