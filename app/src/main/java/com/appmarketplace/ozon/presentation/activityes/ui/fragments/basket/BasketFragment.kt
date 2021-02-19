package com.appmarketplace.ozon.presentation.activityes.ui.fragments.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.appmarketplace.ozon.R


class BasketFragment : Fragment() {

    lateinit var  expressViewModel: BasketViewModel
    var callback: OnBackPressedCallback? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        expressViewModel = ViewModelProvider(this).get(BasketViewModel::class.java)
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