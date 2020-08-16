package com.anji.babydiary.myPage.myFeed

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.anji.babydiary.R
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.databinding.MyFeedFragmentBinding
import com.anji.babydiary.myPage.myFeed.myFeedListAdapter.MyFeedListAdapter

class MyFeed : Fragment() {



    private lateinit var viewModel: MyFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<MyFeedFragmentBinding>(inflater, R.layout.my_feed_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = MainFeedDatabase.getInstance(application).database

        val viewModelFactory = MyFeedViewModelFactory(database, application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyFeedViewModel::class.java)

        binding.myFeedViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val clickAdapter = MyFeedListAdapter(MyFeedClickListener {

        })

        binding.myFeedList.adapter = clickAdapter



        return binding.root
    }



}