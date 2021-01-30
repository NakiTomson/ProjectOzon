package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.basket_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appmarketplace.ozon.R



class BasketFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = BasketFragment()
    }
}