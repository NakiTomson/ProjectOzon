package com.app.marketPlace.presentation.activities.ui.fragments.makingOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.app.marketPlace.R
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_making_order.*
import java.util.ArrayList
import javax.inject.Inject

class MakingOrderFragment : Fragment() {

    init {
        MarketPlaceApp.appComponent.inject(makingOrderFragment = this)
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

        mainViewModel.userLive.observe(viewLifecycleOwner, { user->
            textView8.text = user.address
            textView13.text = user.name+", "+user.phone
        })


        val imagesUrls = arguments?.getStringArrayList("images")

        val oldPrice = arguments?.getString("oldPrice")
        val discount = arguments?.getString("discount")
        val finalPrice = arguments?.getString("finalPrice")

        textView21.text = "$oldPrice"

        if (discount?.toFloat()!! > 0){
            textView23.visibility = View.VISIBLE
            textView22.visibility = View.VISIBLE
            textView23.text = ("- $discount $")
        }

        textView27.text = "$finalPrice"

        buttonMakeSell.setOnClickListener {
            Toast.makeText(context,"Успешно купили",Toast.LENGTH_SHORT).show()
        }

        setUpImages(imagesUrls)
    }

    private fun setUpImages(listImagesUrl: ArrayList<String>?){
        if (listImagesUrl == null) return
        val images = arrayOfNulls<ImageView>(listImagesUrl.size)

        val layoutParams: LinearLayout.LayoutParams =

            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

        for ( i in images.indices){
            images[i] = ImageView(context)
            images[i]?.let {

                Picasso.with(context)
                    .load(listImagesUrl[i])
                    .into(it)

                it.layoutParams = layoutParams
                containerImages.addView(it)
            }
        }
    }

    override fun onDestroyView() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
        super.onDestroyView()
    }
}