package com.appmarketplace.ozon.presentation.activityes.ui.fragments.description

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail.DetailsProductViewModel
import kotlinx.android.synthetic.main.fragment_specifications.*

class SpecificationsFragment(val contextDetail: ViewModelStoreOwner) : Fragment() {

    private lateinit var viewModel: DetailsProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_specifications, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(contextDetail).get(DetailsProductViewModel::class.java)

        viewModel.specifications.observe(viewLifecycleOwner, Observer {
            textSpecification?.text = it ?: "Null"
        })
    }
}