package com.app.marketPlace.presentation.activities.ui.fragments.description

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import com.app.marketPlace.R
import com.app.marketPlace.presentation.activities.ui.fragments.detail.DetailsProductViewModel
import kotlinx.android.synthetic.main.fragment_description.*

class DescriptionFragment : Fragment(R.layout.fragment_description) {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        when(tag){
            "LONG"->{ setDescription( arguments?.getString("longDescription"))}
            "SHORT"->{ setDescription (arguments?.getString("shortDescription"))}
        }
    }

    private fun setDescription(text:String?){
        textDescription.text = text
    }
}