package com.appmarketplace.ozon.presentation.activityes.ui.fragments.basket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.data.utils.Gonfigs.PLAYSTATION
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.presentation.activityes.MainViewModel
import com.appmarketplace.ozon.presentation.adapters.ProductHorizontalItemAdapter
import com.appmarketplace.ozon.presentation.adapters.ProductItemAdapter
import com.appmarketplace.ozon.presentation.rowType.ProductsRowType
import com.appmarketplace.ozon.presentation.rowType.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.product_details_bottom.*
import java.text.DecimalFormat


class BasketFragment : Fragment() {

    lateinit var  viewModel: BasketViewModel
    lateinit var  mainViewModel: MainViewModel

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
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getListSimilarCategory(PLAYSTATION)
        val adapterBakets = ProductHorizontalItemAdapter()
        val adapter  = ProductItemAdapter()
        listRecomedsProduct.adapter = adapter
        listRecomedsProduct.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        adapter.setClickListenerProduct = object : ProductsRowType.OnClickProduct{
            override fun clickProduct(product: OnProductItem, imageView: ImageView) {
                val action = BasketFragmentDirections.actionGlobalDetailsProductFragement3(
                    product = product
                )
                findNavController().navigate(action)
            }
        }

        adapter.setClickHeartProduct = object : ProductsRowType.OnClickHeart{
            override fun onClickHeart(productsItem: OnProductItem) {
                viewModel.insertOrDeleteFavoriteProduct(productsItem)
            }
        }

        adapter.setClickBasketProduct = object : ProductsRowType.OnClickHeart {
            override fun onClickHeart(productsItem: OnProductItem) {


                viewModel.insertOrDeleteBasket(productsItem)

                if (productsItem.productInBasket){

                    adapterBakets.setData(productsItem)
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


        textView29.setOnClickListener {
            requireActivity().bottomNavigationView.selectedItemId = R.id.home
//            mainViewModel.navigateInHome()
        }



        mutipleBaketRecyclerView.layoutManager = LinearLayoutManager(context)
        mutipleBaketRecyclerView.adapter = adapterBakets
        mutipleBaketRecyclerView.setHasFixedSize(true)
        viewModel.getBasket()

        var images:List<String>? = null
        var oldPrice:Float= 0f
        var priceWithdicaount:Float= 0f

        viewModel.productsInBusketLive.observe(viewLifecycleOwner, Observer {

            var priceWithdicaountLocal:Float = 0f
            var priceOldLocal:Float = 0f

            if (it.isEmpty()) {
                frameBusket.visibility = View.VISIBLE
            } else {
                frameBusket.visibility = View.GONE
                adapterBakets.setData(it)

                images = it.map { it.generalIconProductSting.toString() }

                it.forEach { data ->
                    priceOldLocal = priceOldLocal.plus(
                        data.priceOlD?.replace("$", "")?.toFloat()!!
                    )
                }
                it.forEach { data ->
                    priceWithdicaountLocal = priceWithdicaountLocal.plus(
                        data.priceWithDiscount?.replace(
                            "$",
                            ""
                        )?.toFloat()!!
                    )
                }

                priceWithdicaount = priceWithdicaountLocal
                allMaonu.text = "$priceWithdicaountLocal $"

                priceWithdicaountLocal = 0f

                oldPrice = priceOldLocal
                priceOldLocal = 0f

            }
        })

        adapterBakets.setClickHeartProduct = object :ProductsRowType.OnClickHeart{
            override fun onClickHeart(productsItem: OnProductItem) {
                viewModel.insertOrDeleteFavoriteProduct(productsItem)
            }
        }
        adapterBakets.setOnBasketDelete = object :ProductsRowType.OnClickHeart{
            override fun onClickHeart(productsItem: OnProductItem) {
                viewModel.deleteBasket(productsItem)
                adapterBakets.deleteProduct(productsItem)
            }
        }

        val mAuth = FirebaseAuth.getInstance()

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
                        mainViewModel.navigateInHome(R.id.signInFragment)
                    }
                    .setNegativeButton("Нет") { dialog, id -> dialog.cancel()
                    }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()

            }
        }
    }



    fun <T> gettingErrors(resource: Resource<T>): Boolean {
        return !(resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data == null || resource.exception != null)
    }

    fun <T> errorhandling(name: String, resource: Resource<T>) {
        Log.v(name, "${resource.exception?.message}")
        Log.v(name, "${resource.exception}")
        Log.v(name, "${resource.status}")
    }


}