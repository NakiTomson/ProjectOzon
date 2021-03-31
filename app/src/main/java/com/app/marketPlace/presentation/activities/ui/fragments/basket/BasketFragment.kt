package com.app.marketPlace.presentation.activities.ui.fragments.basket

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.data.utils.ConstantsApp.PLAYSTATION
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.adapters.BasketAdapter
import com.app.marketPlace.presentation.adapters.ProductAdapter
import com.app.marketPlace.presentation.interfaces.ProductRowType
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.fragment_basket.startShopping
import java.text.DecimalFormat

@AndroidEntryPoint
class BasketFragment : Fragment(R.layout.fragment_basket) {

    private val viewModel: BasketViewModel by viewModels()

    lateinit var navController: NavController

    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListSimilarCategory(PLAYSTATION)
        navController = findNavController()

        val mAuth = FirebaseAuth.getInstance()

        val basketAdapter = BasketAdapter()
        val recommendAdapter  = ProductAdapter()

        setupBasketAdapter(basketAdapter)
        setupListRecommendAdapter(recommendAdapter,basketAdapter)
        setupRecommendProducts(recommendAdapter)
        setupBasketProducts(mAuth,basketAdapter)

        startShopping.setOnClickListener {
            requireActivity().bottomNavigationView.selectedItemId = R.id.home
        }
    }

    private fun setupBasketAdapter(basketAdapter: BasketAdapter) {
        basketAdapter.setHasStableIds(true)

        basketAdapter.setClickListenerProduct = ProductRowType.ProductClickListener { product, view ->
            val extras = FragmentNavigatorExtras(view to product.generalIconProductSting!!)
            val action = BasketFragmentDirections.actionGlobalDetailsProductFragment(product = product)
            findNavController().navigate(action, extras)
        }
        basketsRecyclerView.layoutManager = LinearLayoutManager(context)

        basketsRecyclerView.apply {
            adapter = basketAdapter
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }

        basketsRecyclerView.setHasFixedSize(true)

        basketAdapter.setClickHeartProduct = ProductRowType.ClickListener { product ->
            mainViewModel.insertOrDeleteFavoriteProduct(product)
        }

        basketAdapter.setOnBasketDelete = ProductRowType.ClickListener { product ->
            mainViewModel.insertOrDeleteBasket(product)
            basketAdapter.deleteProduct(product)
        }
    }

    private fun setupListRecommendAdapter(recommendAdapter: ProductAdapter, basketAdapter: BasketAdapter) {
        listRecommendProduct.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        listRecommendProduct.apply {
            adapter = recommendAdapter
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }

        recommendAdapter.setClickListenerProduct = ProductRowType.ProductClickListener { product, view ->
            val extras = FragmentNavigatorExtras(view to product.generalIconProductSting!!)
            val action = BasketFragmentDirections.actionGlobalDetailsProductFragment(product = product)
            findNavController().navigate(action, extras)
        }

        recommendAdapter.setClickHeartProduct = ProductRowType.ClickListener { product ->
            mainViewModel.insertOrDeleteFavoriteProduct(product)
        }

        recommendAdapter.setClickBasketProduct = ProductRowType.ClickListener { product ->
            mainViewModel.insertOrDeleteBasket(product)
            if (product.productInBasket) {
                basketAdapter.setData(product)
            }
        }
    }

    private fun setupRecommendProducts(recommendAdapter: ProductAdapter) {
        viewModel.productsResultList.observe(viewLifecycleOwner, { resource ->
            recommendAdapter.setData(resource.data!!.list)
        })
    }

    private fun setupBasketProducts(mAuth: FirebaseAuth,basketAdapter: BasketAdapter){
        var images:List<String>? = null
        var oldPrice = 0f
        var priceWithDiscount = 0f

        mainViewModel.baskets.observe(viewLifecycleOwner, {
            val baskets = viewModel.mapperFromDb.mapListBasketDb(it)
            var priceWithDiscountLocal = 0f
            var priceOldLocal = 0f

            if (baskets.isNullOrEmpty()) {
                groupEmptyBasket.visibility = View.VISIBLE
                basketGroup.visibility = View.GONE
            } else {
                groupEmptyBasket.visibility = View.GONE
                basketGroup.visibility = View.VISIBLE
                basketAdapter.setData(baskets)
                images = baskets.map { basketMap -> basketMap.generalIconProductSting.toString() }

                baskets.forEach { data ->
                    priceOldLocal = priceOldLocal.plus(
                        data.priceOlD?.replace("$", "")?.toFloat()!!
                    )
                }

                baskets.forEach { data ->
                    priceWithDiscountLocal = priceWithDiscountLocal.plus(
                        data.priceWithDiscount?.replace("$", "")?.toFloat()!!
                    )
                }

                priceWithDiscount = priceWithDiscountLocal
                allPrice.text = String.format(resources.getString(R.string.priceWithDiscount), priceWithDiscountLocal)
                priceWithDiscountLocal = 0f
                oldPrice = priceOldLocal
                priceOldLocal = 0f
            }
        })

        goToMakeOrder.setOnClickListener {
            handleMakeOrder(mAuth,images,oldPrice,priceWithDiscount)
        }
    }

    private fun handleMakeOrder(mAuth: FirebaseAuth, images: List<String>?, oldPrice: Float, priceWithDiscount: Float) {
        if (mAuth.currentUser != null){
            val bundle = Bundle()
            val arrayList = ArrayList<String>(images)
            bundle.putStringArrayList("images",arrayList)
            bundle.putString("oldPrice", "$oldPrice $")
            val decimalFormat = DecimalFormat("#.#")
            val result: String = decimalFormat.format(oldPrice - priceWithDiscount)
            bundle.putString("discount", result)
            bundle.putString("finalPrice", "$priceWithDiscount $")
            navController.navigate(R.id.makingOrderFragment, bundle)
        }else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.regTitle))
            builder.setMessage(getString(R.string.regMessage))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.regYes)) { dialog, id ->
                    requireActivity().bottomNavigationView.selectedItemId = R.id.home
                }
                .setNegativeButton(getString(R.string.regNo)) { dialog, id -> dialog.cancel()
                }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }
}