package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.appmarketplace.ozon.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {
    lateinit var  navController:NavController
    private lateinit var homeViewModel: HomeFragmentViewModel

    var callback: OnBackPressedCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        refreshFragment()

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressCallBack()
        )

//        navController.addOnNavigatedListener(object : OnNavigatedListener() {
//            fun onNavigated(controller: NavController, destination: NavDestination) {
//                Log.d(TAG, "onNavigate to " + destination.label)
//            }
//        })

    }

    fun backPressCallBack() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navController.popBackStack()
        }
    }


    override fun onHiddenChanged(hidden: Boolean) {
        if (hidden){
            callback?.remove()
        }else{
            callback = backPressCallBack()
            requireActivity().onBackPressedDispatcher.addCallback(this, callback!!)
        }
        super.onHiddenChanged(hidden)
    }


    fun refreshFragment(){
        swipeRefreshLayout.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main){
                    swipeRefreshLayout.isRefreshing = false
                    navController.navigate(R.id.action_generalInnerFragment_to_detailProductFragementHomeFragment)
                }
            }
        }
    }

}