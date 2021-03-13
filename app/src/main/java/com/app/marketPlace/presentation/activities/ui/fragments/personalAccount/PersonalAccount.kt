package com.app.marketPlace.presentation.activities.ui.fragments.personalAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.app.marketPlace.R
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_personal_account.*
import javax.inject.Inject

class PersonalAccount : Fragment() {



    init {
        MarketPlaceApp.appComponent.inject(personalAccount = this)
    }


    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by activityViewModels {
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
            goToAuthentication.setOnClickListener {
                requireActivity().bottomNavigationView.selectedItemId = R.id.home
            }
        }else{
            frameAccount.visibility = View.GONE
            mainViewModel.getUser(mAuth.currentUser?.email.toString())
            mainViewModel.userLive.observe(viewLifecycleOwner, {
                nameUser.setText(it?.name ?: "null")
                phoneUser.setText(it?.phone ?: "null")
                mailUser.setText(it?.mail ?: "null")
                addressUser.setText(it?.address ?: "null")
            })

        }
    }

}