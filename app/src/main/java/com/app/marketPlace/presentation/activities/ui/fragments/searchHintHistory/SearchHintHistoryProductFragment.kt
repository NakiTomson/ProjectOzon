package com.app.marketPlace.presentation.activities.ui.fragments.searchHintHistory

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.marketPlace.R
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.MainViewModel
import com.app.marketPlace.presentation.activities.MainViewModelFactory
import com.app.marketPlace.presentation.adapters.HintSearchProductsAdapter
import kotlinx.android.synthetic.main.fragment_search_hint_history_product.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import javax.inject.Inject


class SearchHintHistoryProductFragment : Fragment() {


    init {
        MarketPlaceApp.appComponent.inject(searchHintHistoryProductFragment = this)
    }


    @Inject
    lateinit var repository: DataBaseRepository

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_hint_history_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val navController = findNavController()

        searchTextInput.isFocusableInTouchMode = true
        searchTextInput.requestFocus()

        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(searchTextInput, InputMethodManager.SHOW_IMPLICIT)


        val adapterHints = HintSearchProductsAdapter()

        adapterHints.hintProductsListener =
            object : HintSearchProductsAdapter.HintProductsListener {
                override fun onHintSelected(hintProduct: String) {
                    startSearchWordRequest(hintProduct, navController)
                }
            }

        listHitProductsSearch.layoutManager = LinearLayoutManager(activity)
        adapterHints.setHasStableIds(true)
        listHitProductsSearch.adapter = adapterHints


        val searchWord = requireArguments().getString("arg1")

        searchWord?.let {
            searchTextInput.setText(it.replace("&search=", " "))
            searchTextInput.setSelection(searchTextInput.text.length)
        }


        mainViewModel.hintProducts?.observe(viewLifecycleOwner, { data ->
            if(data.isEmpty()){
                adapterHints.setHints(mutableListOf("Tv's","iPhone 12 Pro Max", "Samsung"))
            }else{
                adapterHints.setHints(data.map { it.nameRequest }.toMutableList())
            }
        })


        searchTextInput?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (searchTextInput.text.length > 2) {
                        mainViewModel.insertOrDeleteHintsProduct(searchTextInput.text.toString())
                        startSearchWordRequest(searchTextInput.text.toString(), navController)
                    } else {
                        Toast.makeText(activity, "Слишком короткий запрос", Toast.LENGTH_SHORT).show()
                    }
                    return true
                }
                return false
            }
        })


        searchTextInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    fun startSearchWordRequest(keywords: String, navController: NavController) {
        val bundle = Bundle()
        bundle.putString("arg1", keywords.trim().replace(" ", "&search="))
        navController.navigate(R.id.productsListFragment, bundle)
    }

}