package com.appmarketplace.ozon.presentation.activityes


import android.os.*
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.repositories.DataBaseRepository
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.activityes.ui.setupWithNavController
import com.appmarketplace.ozon.presentation.utils.NetworkConnection
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
        OzonApp.appComponent.inject(mainActivity = this)
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

        mainViewModel.allIdsInBaskets.observe(this, Observer {list->
            list?.let {
                listIdsBasket.clear()
                listIdsBasket.addAll(it)
            }
        })

        mainViewModel.allIdsInFavorite.observe(this, Observer {list->
            list?.let {
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
        cheakInternetConnection()

        bottomNavigationView.callOnClick()
    }


    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }


    fun cheakInternetConnection() {
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