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
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory
import com.app.marketPlace.presentation.adapters.ProductBasketAdapter
import com.app.marketPlace.presentation.adapters.ProductItemAdapter
import com.app.marketPlace.presentation.rowType.ProductsRowType
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_basket.*
import java.text.DecimalFormat
import javax.inject.Inject


class BasketFragment : Fragment(R.layout.fragment_basket) {

    init {
        MarketPlaceApp.appComponent.inject(basketFragment = this)
    }

    @Inject
    lateinit var repository: DataBaseRepository

    private val viewModel: BasketViewModel by viewModels()

    lateinit var navController: NavController

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListSimilarCategory(PLAYSTATION)
        navController = findNavController()

        val mAuth = FirebaseAuth.getInstance()

        val basketAdapter = ProductBasketAdapter()
        val recommendAdapter  = ProductItemAdapter()

        setupBasketAdapter(basketAdapter)
        setupListRecommendAdapter(recommendAdapter,basketAdapter)
        setupRecommendProducts(recommendAdapter)
        setupBasketProducts(mAuth,basketAdapter)

        startShopping.setOnClickListener {
            requireActivity().bottomNavigationView.selectedItemId = R.id.home
        }
    }

    private fun setupBasketAdapter(basketAdapter: ProductBasketAdapter) {
        basketAdapter.setHasStableIds(true)
        basketAdapter.setClickListenerProduct = ProductsRowType.ProductClickListener { product, view ->
            val extras = FragmentNavigatorExtras(view to product.generalIconProductSting!!)
            val action = BasketFragmentDirections.actionGlobalDetailsProductFragment(product = product)
            findNavController().navigate(action, extras)
        }
        basketsRecyclerView.layoutManager = LinearLayoutManager(context)
        basketsRecyclerView.adapter = basketAdapter
        basketsRecyclerView.setHasFixedSize(true)

        basketAdapter.setClickHeartProduct = ProductsRowType.ClickListener { product ->
            mainViewModel.insertOrDeleteFavoriteProduct(product)
        }

        basketAdapter.setOnBasketDelete = ProductsRowType.ClickListener { product ->
            mainViewModel.insertOrDeleteBasket(product)
            basketAdapter.deleteProduct(product)
        }
    }

    private fun setupListRecommendAdapter(recommendAdapter: ProductItemAdapter,basketAdapter: ProductBasketAdapter) {
        listRecommendProduct.adapter = recommendAdapter
        listRecommendProduct.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recommendAdapter.setClickListenerProduct = ProductsRowType.ProductClickListener { product, view ->
            val extras = FragmentNavigatorExtras(view to product.generalIconProductSting!!)
            val action = BasketFragmentDirections.actionGlobalDetailsProductFragment(product = product)
            findNavController().navigate(action, extras)
        }

        recommendAdapter.setClickHeartProduct = ProductsRowType.ClickListener { product ->
            mainViewModel.insertOrDeleteFavoriteProduct(product)
        }

        recommendAdapter.setClickBasketProduct = ProductsRowType.ClickListener { product ->
            mainViewModel.insertOrDeleteBasket(product)
            if (product.productInBasket) {
                basketAdapter.setData(product)
            }
        }
    }

    private fun setupRecommendProducts(recommendAdapter: ProductItemAdapter) {
        viewModel.productsResultList.observe(viewLifecycleOwner, { resource ->
            recommendAdapter.setData(resource.data!!.list)
        })
    }

    private fun setupBasketProducts(mAuth: FirebaseAuth,basketAdapter: ProductBasketAdapter){
        var images:List<String>? = null
        var oldPrice = 0f
        var priceWithDiscount = 0f

        mainViewModel.baskets.observe(viewLifecycleOwner, {
            val baskets = Mapper.MapperToUi.mapListBasketDb(it)
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
            builder.setTitle("Вы не зарегестрировались")
            builder.setMessage("Чтобы купить товар необходимо зарегистрироваться")
                .setCancelable(false)
                .setPositiveButton("Да") { dialog, id ->
                    requireActivity().bottomNavigationView.selectedItemId = R.id.home
                }
                .setNegativeButton("Нет") { dialog, id -> dialog.cancel()
                }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }
}