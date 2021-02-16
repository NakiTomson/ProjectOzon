package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment



import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.appmarketplace.ozon.R


class HomeFragment : Fragment() {
    lateinit var  navController:NavController
    private lateinit var homeViewModel: HomeFragmentViewModel

    var hide:Boolean = false

    var callback: OnBackPressedCallback? = null

    var statePopOut:Boolean = false
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
//        refreshFragment()

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressCallBack()
        )


        val editText = activity?.findViewById<EditText>(R.id.searchTextInput)


        editText?.setOnTouchListener { v, event ->

            if (!hide){
                if (navController.currentDestination?.id  == R.id.productSearchListHomeFragment && statePopOut ){
                    navController.popBackStack()
                }else if (navController.currentDestination?.id != R.id.searchListProductHomeFragment) {
                    try {
                        navController.navigate(R.id.searchListProductHomeFragment)
                        statePopOut = true
                    } catch (e: Exception) {

                    }
                }
                v.performClick()
                v.onTouchEvent(event)
            }else{
                false
            }
        }

    }

    fun backPressCallBack() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (navController.backStack.size >2){
                navController.popBackStack()
            }
        }
    }


    override fun onHiddenChanged(hidden: Boolean) {
        hide = hidden
        if (hidden){
            callback?.remove()
        }else{
            callback = backPressCallBack()
            requireActivity().onBackPressedDispatcher.addCallback(this, callback!!)
        }
        super.onHiddenChanged(hidden)
    }


//    fun refreshFragment(){
//        swipeRefreshLayout.setOnRefreshListener {
//            GlobalScope.launch(Dispatchers.IO) {
//                withContext(Dispatchers.Main){
//                    swipeRefreshLayout.isRefreshing = false
//                    navController.navigate(R.id.action_generalInnerFragment_to_detailProductFragementHomeFragment)
//                }
//            }
//        }
//    }


}

