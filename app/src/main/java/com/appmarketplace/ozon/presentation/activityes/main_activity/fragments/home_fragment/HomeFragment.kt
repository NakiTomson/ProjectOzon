package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appmarketplace.ozon.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        refreshFragment()
    }


    fun refreshFragment(){
        swipeRefreshLayout.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.IO) {
                Thread.sleep(1000)
                withContext(Dispatchers.Main){
                    swipeRefreshLayout.isRefreshing = false
                }
            }

        }
    }

}