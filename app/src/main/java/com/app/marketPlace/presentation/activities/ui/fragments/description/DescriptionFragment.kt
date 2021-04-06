package com.app.marketPlace.presentation.activities.ui.fragments.description

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.marketPlace.R
import kotlinx.android.synthetic.main.fragment_description.*

class DescriptionFragment : Fragment(R.layout.fragment_description) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(tag){
            "LONG"-> { setDescription( arguments?.getString("longDescription"))}
            "SHORT"-> { setDescription (arguments?.getString("shortDescription"))}
        }
    }

    private fun setDescription(text:String?){
        textDescription.text = text
    }
}