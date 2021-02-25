package com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnBoardingItem
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.description.DescriptionFragment
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.description.SpecificationsFragment
import com.appmarketplace.ozon.presentation.adapters.BannerAdapter
import com.appmarketplace.ozon.presentation.adapters.ProductItemAdapter
import com.appmarketplace.ozon.presentation.adapters.SimpleDataAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details_product.*
import kotlinx.android.synthetic.main.product_details_bottom.*
import kotlinx.android.synthetic.main.product_details_center.*


class DetailsProductFragement : Fragment() {


    private lateinit var viewModel: DetailsProductViewModel

    val args: DetailsProductFragementArgs by navArgs()


    var transaction:Boolean = false

    val description = DescriptionFragment()
    val specifications = SpecificationsFragment()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsProductViewModel::class.java)
        val detailProduct = args.product


        val nameProduct:String =
            detailProduct?.nameOfProduct!!.replace("-", "").replace("  ", " ")


        val searchWord = nameProduct
            .substring(0, nameProduct.indexOf(' ', nameProduct.indexOf(' ') + 7))
            .trim()



//        viewModel.getListEquivalentProducts(searchWord)

//        detailProduct?.categoryPath?.let { viewModel.getListSimilarCategory(it) }

        var activeFragment:Fragment = description

        val baket = activity?.findViewById<MaterialButton>(R.id.inBasketButton)
        baket?.visibility = View.VISIBLE
        baket?.text = "В корзину                 "+detailProduct?.priceWithDiscount


        val adapterImages = BannerAdapter()
        detailProduct?.images?.forEach {
            adapterImages.setItem(OnBoardingItem(onBoardingImageUrl = it))
        }


        imageDetailViewPager.adapter  = adapterImages
        setupIndicator(adapterImages.itemCount)
        setCurrentIndicator(0)

        imageDetailViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })


        if (!transaction){
            childFragmentManager.beginTransaction().apply {
                add(R.id.frameDescriptionContainer, description, "description").show(description)
                add(R.id.frameDescriptionContainer, specifications, "specifications").hide(
                    specifications
                )
            }.commit()
        }
        transaction = true



        tabsLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        childFragmentManager.beginTransaction().hide(activeFragment)
                            .show(description).commit()
                        activeFragment = description

                    }
                    1 -> {
                        childFragmentManager.beginTransaction().hide(activeFragment)
                            .show(specifications).commit()
                        activeFragment = specifications
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })



        val simpleAdapter = SimpleDataAdapter()
        simpleAdapter.setData(detailProduct?.categoryPath?.toMutableList())
        similarCategory.layoutManager = LinearLayoutManager(context)
        similarCategory.adapter = simpleAdapter



        val adapterEquivalent  = ProductItemAdapter()

        listProductsEquivalent.adapter = adapterEquivalent
        listProductsEquivalent.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val adapterSimilar  = ProductItemAdapter()

        listProductsSimilar.adapter = adapterSimilar
        listProductsSimilar.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )


        viewModel.searchProductsResultList.observe(viewLifecycleOwner, Observer {
            it.data?.let { list -> adapterEquivalent.setData(list.list) }
        })

        viewModel.productsResultList.observe(viewLifecycleOwner, Observer {
            it.data?.let { list -> adapterSimilar.setData(list.list) }
        })
    }


    private fun setCurrentIndicator(position: Int) {
        val childCount  = indicatorsContainer.childCount

        for (i in 0 until  childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }

    private fun setupIndicator(itemCount: Int) {
        val indicators = arrayOfNulls<ImageView>(itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for ( i in indicators.indices){
            indicators[i] = ImageView(context)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.findViewById<MaterialButton>(R.id.inBasketButton)?.visibility = View.GONE
    }

}
