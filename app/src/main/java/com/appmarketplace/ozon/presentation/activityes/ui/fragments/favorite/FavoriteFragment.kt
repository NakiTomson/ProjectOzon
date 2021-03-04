package com.appmarketplace.ozon.presentation.activityes.ui.fragments.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.domain.repositories.DataBaseRepository
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.activityes.MainViewModel
import com.appmarketplace.ozon.presentation.activityes.MainViewModelFactory
import com.appmarketplace.ozon.presentation.adapters.ProductItemAdapter
import com.appmarketplace.ozon.presentation.rowType.ProductsRowType
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    init {
        OzonApp.appComponent.inject(favoriteFragment = this)
    }

    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favoriteList.layoutManager = GridLayoutManager(activity,2)
        val productsAdapter = ProductItemAdapter()
        productsAdapter.setHasStableIds(true)
        favoriteList.adapter = productsAdapter

        productsAdapter.setClickHeartProduct = object :ProductsRowType.OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                mainViewModel.deleteProduct(productsItem)
                productsAdapter.deleteProduct(productsItem)
            }
        }

        productsAdapter.setClickListenerProduct = object : ProductsRowType.OnProductClickListener {
            override fun clickProduct(product: OnProductItem, imageView: ImageView) {

                val extras = FragmentNavigatorExtras(
                    imageView to product.images?.get(0)!!
                )

                val action = FavoriteFragmentDirections.actionGlobalDetailsProductFragement2(
                    product = product,
                    transitionName = product.images.get(0)
                )
                findNavController().navigate(action,extras)
            }
        }


        mainViewModel.favorite.observe(viewLifecycleOwner, Observer { list ->

            if (list.isNullOrEmpty()) frameFavorite.visibility = View.VISIBLE else frameFavorite.visibility = View.GONE
            if (list == null) return@Observer
            productsAdapter.setData(
                list.map {
                    OnProductItem(
                        type = OnProductItem.Type.ProductWithName,
                        generalIconProductSting = it.iconProduct,
                        favoritelIconProduct = it.isFavorite,
                        productDiscount = it.productDiscount,
                        isBestseller = it.isBestseller,
                        priceWithDiscount = it.priceWithDiscount,
                        priceOlD = it.priceOlD,
                        goToBasket = it.goToBasket,
                        nameOfProduct = it.nameOfProduct,
                        shortDescription = it.shortDescription,
                        longDescription = it.longDescription,
                        images = it.images,
                        company = it.company,
                        color = it.color,
                        skuId = it.id
                    )
                })
        })
    }

}

