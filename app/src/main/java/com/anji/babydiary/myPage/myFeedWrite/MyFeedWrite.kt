package com.anji.babydiary.myPage.myFeedWrite

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.databinding.MyFeedWriteFragmentBinding
import com.anji.babydiary.myPage.myFeed.MyFeedDirections

class MyFeedWrite : Fragment() {


    private lateinit var viewModel: MyFeedWriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<MyFeedWriteFragmentBinding>(inflater, R.layout.my_feed_write_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = MainFeedDatabase.getInstance(application).database

        val viewModelFactory = MyFeedWriteViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyFeedWriteViewModel::class.java)

        binding.writeViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        binding.openKidInfo.setOnCheckedChangeListener { compoundButton, b ->

            if (b) {
                viewModel.isShown.value = View.VISIBLE
            }else {
                viewModel.isShown.value = View.GONE
            }
        }

        binding.tolocationLayer.setOnClickListener {
            findNavController().navigate(MyFeedWriteDirections.actionMyFeedWriteToMyFeedWriteLocation())
        }

        return binding.root
    }



}