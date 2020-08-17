package com.anji.babydiary.myPage.myFeedWriteLocation

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.createViewModelLazy
import com.anji.babydiary.R
import com.anji.babydiary.databinding.MyFeedWriteLocationFragmentBinding

class MyFeedWriteLocation : Fragment() {



    private lateinit var viewModel: MyFeedWriteLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<MyFeedWriteLocationFragmentBinding>(inflater, R.layout.my_feed_write_location_fragment, container, false)

        val viewModelFactory = MyFeedWriteLocationViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyFeedWriteLocationViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyFeedWriteLocationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}