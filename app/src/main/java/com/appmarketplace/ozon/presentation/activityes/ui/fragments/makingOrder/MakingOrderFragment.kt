package com.appmarketplace.ozon.presentation.activityes.ui.fragments.makingOrder

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.repositories.DataBaseRepository
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.activityes.MainViewModel
import com.appmarketplace.ozon.presentation.activityes.MainViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_making_order.*
import javax.inject.Inject

class MakingOrderFragment : Fragment() {




    init {
        OzonApp.appComponent.inject(makingOrderFragment = this)
    }


    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_making_order, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mAuth = FirebaseAuth.getInstance()
        mainViewModel.getUser(mAuth.currentUser?.email!!)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE

        mainViewModel.userLive.observe(viewLifecycleOwner, Observer {user->
            textView8.text = user.address
            textView13.text = user.name+", "+user.phone
        })



        val imageUrl = arguments?.getString("imageUrl0")
        val imageUrl2 = arguments?.getString("imageUrl1")
        val imageUrl3 = arguments?.getString("imageUrl2")
        val oldPrice = arguments?.getString("oldPrice")
        val discount = arguments?.getString("discount")
        val finalPrice = arguments?.getString("finalPrice")

        Picasso.with(context)
            .load(imageUrl)
            .into(imageView5)

        textView21.text = "$oldPrice "

        if (discount?.toFloat()!! > 0){
            textView23.visibility = View.VISIBLE
            textView22.visibility = View.VISIBLE
            textView23.text = ("- $discount $")
        }

        textView27.text = "$finalPrice "

        buttonMakeSell.setOnClickListener {
            Toast.makeText(context,"Успешно купили",Toast.LENGTH_SHORT).show()
        }

        if (imageUrl2 != null){
            imageView6.visibility = View.VISIBLE

            Picasso.with(context)
                .load(imageUrl2)
                .into(imageView6)
        }

        if (imageUrl3 != null){
            imageView7.visibility = View.VISIBLE

            Picasso.with(context)
                .load(imageUrl3)
                .into(imageView7)
        }

    }

    override fun onDestroyView() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
        super.onDestroyView()
    }
}