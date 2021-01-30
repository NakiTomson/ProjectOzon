package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.express_fragment


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appmarketplace.ozon.R

class ExpressFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_express, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = ExpressFragment()
    }
}