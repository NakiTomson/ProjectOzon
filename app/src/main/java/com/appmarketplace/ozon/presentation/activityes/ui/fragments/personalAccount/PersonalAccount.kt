package com.appmarketplace.ozon.presentation.activityes.ui.fragments.personalAccount

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.activityes.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_personal_account.*

class PersonalAccount : Fragment() {


    private lateinit var viewModel: PersonalAccountViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonalAccountViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser == null){
            goToAuntification.setOnClickListener {
                requireActivity().bottomNavigationView.selectedItemId = R.id.home
                mainViewModel.navigateInHome(R.id.signInFragment)
            }
        }else{
            frameAccount.visibility = View.GONE
            viewModel.getUSer(mAuth.currentUser?.email.toString())
            viewModel.userLive.observe(viewLifecycleOwner, Observer {
                nameUser.setText(it?.name ?: "null")
                phoneUser.setText(it?.phone ?: "null")
                mailUser.setText(it?.mail ?: "null")
                adressUser.setText(it?.address ?: "null")
            })

        }
    }

}