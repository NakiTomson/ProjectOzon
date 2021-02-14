package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.express_fragment


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appmarketplace.ozon.R

class ExpressFragment : Fragment() {


    lateinit var  expressViewModel:ExpressViewModel
    var callback: OnBackPressedCallback? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_express, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expressViewModel = ViewModelProvider(this).get(ExpressViewModel::class.java)
    }

    private fun backPressCallBack() = object : OnBackPressedCallback(true ) {
        override fun handleOnBackPressed() {

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

}