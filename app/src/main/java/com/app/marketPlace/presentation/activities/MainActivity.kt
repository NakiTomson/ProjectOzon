package com.app.marketPlace.presentation.activities


import android.os.*
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.app.marketPlace.R
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.ui.setupWithNavController
import com.app.marketPlace.presentation.utils.NetworkConnection
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


    private var currentNavController: LiveData<NavController>? = null


    companion object{
        var listIdsBasket:MutableList<Int> = mutableListOf()
        var listIdsFavorite:MutableList<Int> = mutableListOf()
    }

    init {
        MarketPlaceApp.appComponent.inject(mainActivity = this)
    }


    @Inject
    lateinit var repository:DataBaseRepository

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        mainViewModel.allIdsInBaskets.observe(this, { list->
            list?.let {
                Log.v("CRTYBUN","re $it")
                listIdsBasket.clear()
                listIdsBasket.addAll(it)
            }
        })

        mainViewModel.allIdsInFavorite.observe(this, { list->
            list?.let {
                Log.v("CRTYBUN","re2 $it")
                listIdsFavorite.clear()
                listIdsFavorite.addAll(it)
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
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, { isConnected ->
            if (isConnected) {
                connectionFrame.visibility = View.GONE
            } else {
                connectionFrame.visibility = View.VISIBLE
            }
        })
    }

}