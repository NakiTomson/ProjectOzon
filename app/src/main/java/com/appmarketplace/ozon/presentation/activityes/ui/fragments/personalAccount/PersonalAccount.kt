package com.appmarketplace.ozon.presentation.activityes.ui.fragments.personalAccount

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.repositories.DataBaseRepository
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.activityes.MainViewModel
import com.appmarketplace.ozon.presentation.activityes.MainViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_personal_account.*
import javax.inject.Inject

class PersonalAccount : Fragment() {



    init {
        OzonApp.appComponent.inject(personalAccount = this)
    }


    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser == null){
            goToAuntification.setOnClickListener {
                requireActivity().bottomNavigationView.selectedItemId = R.id.home
            }
        }else{
            frameAccount.visibility = View.GONE
            mainViewModel.getUser(mAuth.currentUser?.email.toString())
            mainViewModel.userLive.observe(viewLifecycleOwner, Observer {
                nameUser.setText(it?.name ?: "null")
                phoneUser.setText(it?.phone ?: "null")
                mailUser.setText(it?.mail ?: "null")
                adressUser.setText(it?.address ?: "null")
            })

        }
    }

}