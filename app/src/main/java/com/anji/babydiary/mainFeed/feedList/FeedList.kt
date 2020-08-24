package com.anji.babydiary.mainFeed.feedList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.databinding.FeedListFragmentBinding
import com.anji.babydiary.mainFeed.feedList.listAdapter.MainFeedListAdapter

class FeedList : Fragment() {

    private lateinit var viewModel: FeedListViewModel
    private lateinit var viewModelFactory: FeedListViewModelFactory
    //private lateinit var binding:FeedListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FeedListFragmentBinding>(inflater, R.layout.feed_list_fragment, container, false)
        //viewModel = ViewModelProviders.of(this).get(FeedListViewModel::class.java)

        val application = requireNotNull(this.activity).application
        val dataSource = MainFeedDatabase.getInstance(application).database

        viewModelFactory = FeedListViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedListViewModel::class.java)

        binding.mainFeed = viewModel

        val adapter = MainFeedListAdapter(FeedClickListener {
            findNavController().navigate(FeedListDirections.actionFeedListToFeedDetail(it))
        })
        binding.feedList.adapter = adapter

        binding.setLifecycleOwner(this)

        viewModel.selectAll()
        viewModel.allFeeds.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })



        return binding.root
    }

}