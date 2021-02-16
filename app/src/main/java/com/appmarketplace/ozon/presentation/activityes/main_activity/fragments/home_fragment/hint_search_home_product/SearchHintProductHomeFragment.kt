package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.hint_search_home_product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmarketplace.ozon.R
import com.appmarketplace.ozon.presentation.adapters.HintSearchProductsAdapter
import kotlinx.android.synthetic.main.search_list_product_home_fragment.*

class SearchHintProductHomeFragment : Fragment() {

    private lateinit var viewModel: SearchHintProductHomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_list_product_home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SearchHintProductHomeViewModel::class.java)

        viewModel.getHintText()

        val adapterHints = HintSearchProductsAdapter()
        listHitProductsSearch.layoutManager = LinearLayoutManager(activity)
        adapterHints.setHasStableIds(true)
        listHitProductsSearch.adapter = adapterHints

        viewModel.liveDataHints.observe(viewLifecycleOwner, Observer { data ->
            adapterHints.setHints(data.toMutableList())
        })

        val editText = activity?.findViewById<EditText>(R.id.searchTextInput)

        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


        val navHostFragment = findNavController()

        editText?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                    if (editText.text.length  > 2){
                        val keywords = editText.text.toString().trim().replace(" ","&search=")
                        viewModel.startSearchProduct(keyWordOne = keywords)
                        navHostFragment.navigate(R.id.action_searchListProductHomeFragment_to_productSearchListHomeFragment)
                    }else{
                        Toast.makeText(activity,"Слишком короткий запрос",Toast.LENGTH_SHORT).show()
                    }

                    return true
                }
                return false
            }
        })
    }

}