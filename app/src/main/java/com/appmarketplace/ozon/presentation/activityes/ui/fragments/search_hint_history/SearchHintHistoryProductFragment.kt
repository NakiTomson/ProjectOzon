package com.appmarketplace.ozon.presentation.activityes.ui.fragments.search_hint_history

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.data.models.HintProductDB
import com.appmarketplace.ozon.presentation.adapters.HintSearchProductsAdapter
import kotlinx.android.synthetic.main.fragment_search_hint_history_product.*
import kotlinx.android.synthetic.main.toolbar_custom.*


class SearchHintHistoryProductFragment : Fragment() {

    private lateinit var viewModel: SearchHintHistoryProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_hint_history_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchHintHistoryProductViewModel::class.java)
        viewModel.getHints()


        val navController = findNavController()

        searchTextInput.isFocusableInTouchMode = true
        searchTextInput.requestFocus();

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


        viewModel.liveDataHints.observe(viewLifecycleOwner, Observer { data ->
            adapterHints.setHints(data.map { it.nameRequset }.toMutableList())
        })


        searchTextInput?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (searchTextInput.text.length > 2) {
                        viewModel.setHint(HintProductDB(searchTextInput.text.toString()))
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