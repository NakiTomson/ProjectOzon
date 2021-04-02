package com.app.marketPlace.presentation.activities.ui.fragments.makingOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.app.marketPlace.R
import com.app.marketPlace.presentation.activities.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_making_order.*
import java.util.ArrayList

@AndroidEntryPoint
class MakingOrderFragment : Fragment(R.layout.fragment_making_order) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mAuth = FirebaseAuth.getInstance()
        mainViewModel.getUser(mAuth.currentUser?.email!!)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE

        mainViewModel.userLive.observe(viewLifecycleOwner, { user->
            deliveryAddress.text = user.address
            nameAndNumberOfUser.text = user.name+", "+user.phone
        })

        val imageUrls = arguments?.getStringArrayList("images")

        val oldPrice = arguments?.getString("oldPrice")
        val discount = arguments?.getString("discount")
        val finalCost = arguments?.getString("finalPrice")

        prodcutsPriceNumber.text = "$oldPrice"

        if (discount?.toFloat()!! > 0){
            textView23.visibility = View.VISIBLE
            textView22.visibility = View.VISIBLE
            textView23.text = ("- $discount $")
        }

        finalPrice.text = "$finalCost"

        buttonMakePurchase.setOnClickListener {
            Toast.makeText(context,"Успешно купили",Toast.LENGTH_SHORT).show()
        }

        setUpImages(imageUrls)
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