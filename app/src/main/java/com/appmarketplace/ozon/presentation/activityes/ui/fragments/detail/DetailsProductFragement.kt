package com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnBoardingItem
import com.appmarketplace.ozon.presentation.adapters.BannerAdapter
import com.appmarketplace.ozon.presentation.adapters.MultipleTypesAdapter
import com.appmarketplace.ozon.presentation.rowType.BannerRowType
import com.appmarketplace.ozon.presentation.rowType.ComplexSloganRowType
import com.appmarketplace.ozon.presentation.rowType.ComplexSloganRowType.Item
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_details_product.*

class DetailsProductFragement : Fragment() {


    private lateinit var viewModel: DetailsProductViewModel

    val args: DetailsProductFragementArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsProductViewModel::class.java)


        val detailProduct = args.product


        val adapterMultiple = MultipleTypesAdapter()
        adapterMultiple.setHasStableIds(true)
        mutipleDetailRecyclerView.layoutManager =  LinearLayoutManager(context)
        mutipleDetailRecyclerView.adapter = adapterMultiple
        mutipleDetailRecyclerView.setHasFixedSize(false)

        val startBannerAdapterViewPager = BannerAdapter()

        detailProduct?.images?.forEach {
            startBannerAdapterViewPager.setItem(OnBoardingItem(onBoardingImageUrl = it))
        }

        adapterMultiple.setData(BannerRowType(startBannerAdapterViewPager,1000))

        activity?.findViewById<MaterialButton>(R.id.inBasketButton)?.visibility = View.VISIBLE
//        if (detailProduct!!.isBestseller){
//            adapterMultiple.setData(ComplexSloganRowType(Item.setBestseller(detailProduct.company!!)))
//        }
//
//        adapterMultiple.setData(ComplexSloganRowType(Item.setPrice(detailProduct.priceWithDiscount!!,detailProduct.priceOlD!!)))
//        adapterMultiple.setData(ComplexSloganRowType(Item.setSimpleOffer("Нашли Дешевле?")))
//        adapterMultiple.setData(ComplexSloganRowType(Item.setSimpleOffer("Примененные скидки")))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.findViewById<MaterialButton>(R.id.inBasketButton)?.visibility = View.GONE
    }

}