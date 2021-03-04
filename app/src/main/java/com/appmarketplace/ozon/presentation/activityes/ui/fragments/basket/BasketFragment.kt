package com.appmarketplace.ozon.presentation.activityes.ui.fragments.basket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.data.utils.Gonfigs.PLAYSTATION
import com.appmarketplace.ozon.domain.mappers.Mapper
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.domain.repositories.DataBaseRepository
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.activityes.MainViewModel
import com.appmarketplace.ozon.presentation.activityes.MainViewModelFactory
import com.appmarketplace.ozon.presentation.activityes.errorhandling
import com.appmarketplace.ozon.presentation.activityes.gettingErrors
import com.appmarketplace.ozon.presentation.adapters.ProductHorizontalItemAdapter
import com.appmarketplace.ozon.presentation.adapters.ProductItemAdapter
import com.appmarketplace.ozon.presentation.rowType.ProductsRowType
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_basket.*
import java.text.DecimalFormat
import javax.inject.Inject


class BasketFragment : Fragment() {

    lateinit var  viewModel: BasketViewModel

    init {
        OzonApp.appComponent.inject(basketFragment = this)
    }

    @Inject
    lateinit var repository: DataBaseRepository

    @Inject
    lateinit var mapper: Mapper

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BasketViewModel::class.java)

        viewModel.getListSimilarCategory(PLAYSTATION)

        val mAuth = FirebaseAuth.getInstance()

        val basketAdapter = ProductHorizontalItemAdapter()
        basketAdapter.setHasStableIds(true)

        val adapter  = ProductItemAdapter()
        listRecommendsProduct.adapter = adapter
        listRecommendsProduct.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        adapter.setClickListenerProduct = object : ProductsRowType.OnProductClickListener{
            override fun clickProduct(product: OnProductItem, imageView: ImageView) {
                val action = BasketFragmentDirections.actionGlobalDetailsProductFragement3(
                    product = product
                )
                findNavController().navigate(action)
            }
        }

        adapter.setClickHeartProduct = object : ProductsRowType.OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
            }
        }

        adapter.setClickBasketProduct = object : ProductsRowType.OnClickListener {
            override fun onClick(productsItem: OnProductItem) {

                mainViewModel.insertOrDeleteBasket(productsItem)

                if (productsItem.productInBasket){
                    basketAdapter.setData(productsItem)
                }
            }
        }

        viewModel.productsResultList.observe(viewLifecycleOwner, Observer { resource ->
            if (gettingErrors(resource)) {
                resource.data?.list?.let { list ->
                    if (list.isEmpty()) errorhandling("Empty List Similar Product", resource)
                    adapter.setData(list)
                }
            } else {
                errorhandling("ERROR PRODUCT 1", resource)
            }
        })


        startShopping.setOnClickListener {
            requireActivity().bottomNavigationView.selectedItemId = R.id.home
        }


        basketsRecyclerView.layoutManager = LinearLayoutManager(context)
        basketsRecyclerView.adapter = basketAdapter
        basketsRecyclerView.setHasFixedSize(true)


        var images:List<String>? = null
        var oldPrice:Float= 0f
        var priceWithdicaount:Float= 0f

        mainViewModel.baskets?.observe(viewLifecycleOwner, Observer {

            Log.v("TAGDATA","newData")
            val baskets = mapper.mapDbBasketToUi(it)

            var priceWithDiscountLocal:Float = 0f

            var priceOldLocal:Float = 0f

            if (baskets.isNullOrEmpty()) {
                groupEmptyBasket.visibility = View.VISIBLE
                basketGroup.visibility = View.GONE
            } else {
                groupEmptyBasket.visibility = View.GONE
                basketGroup.visibility = View.VISIBLE


                basketAdapter.setData(baskets)
                images = baskets.map { basketMap-> basketMap.generalIconProductSting.toString() }

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

                priceWithdicaount = priceWithDiscountLocal
                allPrice.text = "$priceWithDiscountLocal $"
                priceWithDiscountLocal = 0f
                oldPrice = priceOldLocal
                priceOldLocal = 0f

            }
        })

        basketAdapter.setClickHeartProduct = object :ProductsRowType.OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                mainViewModel.insertOrDeleteFavoriteProduct(productsItem)
            }
        }

        basketAdapter.setOnBasketDelete = object :ProductsRowType.OnClickListener{
            override fun onClick(productsItem: OnProductItem) {
                mainViewModel.insertOrDeleteBasket(productsItem)
                basketAdapter.deleteProduct(productsItem)
            }
        }


        val navController = findNavController()
        goToMakeOrder.setOnClickListener {
            if (mAuth.currentUser != null){
                val bundle = Bundle()
                for ((index,value) in (images!!).withIndex()){
                    bundle.putString("imageUrl$index", value)
                }
                bundle.putString("oldPrice", "$oldPrice $")
                val decimalFormat = DecimalFormat("#.#")
                val result: String = decimalFormat.format(oldPrice-priceWithdicaount)
                bundle.putString("discount", "$result")
                bundle.putString("finalPrice", "$priceWithdicaount $")
                navController.navigate(R.id.makingOrderFragment2, bundle)
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




}