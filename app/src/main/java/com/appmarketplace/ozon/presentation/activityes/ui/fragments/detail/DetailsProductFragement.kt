package com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appmarketplace.ozon.R

class DetailsProductFragement : Fragment() {

    companion object {
        fun newInstance() = DetailsProductFragement()
    }

    private lateinit var viewModel: DetailsProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsProductViewModel::class.java)

    }

}