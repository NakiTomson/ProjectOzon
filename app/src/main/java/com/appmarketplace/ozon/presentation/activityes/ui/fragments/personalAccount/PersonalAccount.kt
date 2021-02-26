package com.appmarketplace.ozon.presentation.activityes.ui.fragments.personalAccount

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appmarketplace.ozon.R

class PersonalAccount : Fragment() {

    companion object {
        fun newInstance() = PersonalAccount()
    }

    private lateinit var viewModel: PersonalAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonalAccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

}