package com.anji.babydiary.mainFeed.feedDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.anji.babydiary.R
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.databinding.FeedDetailFragmentBinding

class FeedDetail : Fragment() {

    private lateinit var viewModel: FeedDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argment = arguments?.let { FeedDetailArgs.fromBundle(it) }


        val binding = DataBindingUtil.inflate<FeedDetailFragmentBinding>(inflater, R.layout.feed_detail_fragment, container, false)

        val application = requireNotNull(activity).application
        val database = MainFeedDatabase.getInstance(application).database

        val viewModelFactory = FeedDetailViewModelFactory(argment!!.feedIdx, database)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedDetailViewModel::class.java)

        binding.feedViewModel = viewModel

        viewModel.select.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.e("result","=======================================================================")
                Log.e("result","${it}")
                Log.e("result","=======================================================================")
                binding.writtenDate.text = Utils.getDate(it.date,"YYYY. MM. dd")
            }
        })

        return binding.root
    }

}