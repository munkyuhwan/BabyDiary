package com.anji.babydiary.myPage.myFeed

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyFeedViewModel::class.java)

        binding.myFeedViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val clickAdapter = MyFeedListAdapter(MyFeedClickListener {
            it?.let {
                findNavController().navigate(MyFeedDirections.actionMyFeedToMyFeedDetail(it))
            }
        })
        binding.myFeedList.adapter = clickAdapter

        val manager = GridLayoutManager(activity,3)
        binding.myFeedList.layoutManager = manager

        binding.addMyfeed.setOnClickListener {
            findNavController().navigate(MyFeedDirections.actionMyFeedToMyFeedWrite())
        }

        viewModel.selectAll.observe(viewLifecycleOwner, Observer {
            Log.e("selectAll","================================================================================")
            Log.e("selectAll","${it}")
            Log.e("selectAll","================================================================================")
            it?.let {
                clickAdapter.submitList(it)
            }
        })

        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(context, "activity result", Toast.LENGTH_SHORT).show()
        if (requestCode == CommonCode.MYPAGE_ACTIVITY_RESULT && resultCode == Activity.RESULT_OK) {
            data?.let {
                it.extras?.let {
                    val itemSelected = it.get("selectedItem")

                    when (itemSelected) {
                        1 -> {
                            findNavController().navigate(MyFeedDirections.actionMyFeedToMyProfile())
                        }
                    }

                }




            }


        }


    }




}