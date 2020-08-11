package com.anji.babydiary.shopping.listFragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.anji.babydiary.R
import com.anji.babydiary.database.shopping.Shopping
import com.anji.babydiary.database.shopping.ShoppingDatabase
import com.anji.babydiary.databinding.ActivityShoppingBinding
import com.anji.babydiary.databinding.ShopListFragmentBinding
import com.anji.babydiary.mainFeed.feedList.FeedClickListener
import com.anji.babydiary.mainFeed.feedList.listAdapter.MainFeedListAdapter
import com.anji.babydiary.shopping.listFragment.listAdapter.ShoppingListAdapter

class ShopListFragment : Fragment() {

    private lateinit var viewModel: ShopListViewModel
    private lateinit var viewModelFactory:ShopListViewModelFactory
    private lateinit var binding:ShopListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<ShopListFragmentBinding>(inflater, R.layout.shop_list_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = ShoppingDatabase.getInstance(application).database

        viewModelFactory = ShopListViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShopListViewModel::class.java)

        binding.shopModelView = viewModel

        val adapter = ShoppingListAdapter(ProductClickListener {
            Log.e("click", "view clicked")
            //Toast.makeText(application,"${it}", Toast.LENGTH_SHORT).show()
        })
        binding.productList.adapter = adapter

        binding.setLifecycleOwner(this)

        viewModel.selectAll()
        viewModel.allProduct.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.e("products","===============================================")
                Log.e("products","${it}")
                Log.e("products","===============================================")
                adapter.submitList(it)
            }
        })

        val manager = GridLayoutManager(activity,2)
        binding.productList.layoutManager = manager

        binding.writeProduct.setOnClickListener {
            
            findNavController().navigate(ShopListFragmentDirections.actionShopListFragmentToWriteProduct(1))
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShopListViewModel::class.java)
    }

}