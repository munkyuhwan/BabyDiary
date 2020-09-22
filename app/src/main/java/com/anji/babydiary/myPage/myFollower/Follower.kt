package com.anji.babydiary.myPage.myFollower

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.follow.FollowDatabase
import com.anji.babydiary.databinding.FollowerFragmentBinding
import com.anji.babydiary.event.eventDetail.EventDetailArgs
import com.anji.babydiary.myPage.myFollower.listAdapter.FollowListAdapter

class Follower : Fragment() {



    private lateinit var viewModel: FollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FollowerFragmentBinding>(inflater, R.layout.follower_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = FollowDatabase.getInstance(application).database

        val viewModelFactory = FollowerViewModelFactory(database, MyShare.prefs.getLong("idx", 0L), application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FollowerViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.followerViewModel = viewModel


        val argument = arguments?.let { FollowerArgs.fromBundle(it) }
        val type = argument!!.type


        val adapter = FollowListAdapter(requireActivity(), type, viewLifecycleOwner)
        binding.followList.adapter = adapter

        if (type.equals("follower")) {
            binding.completeButton.text = "팔로워"
            viewModel.selectAllFollower.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })
        }

        if (type.equals("following")) {
            binding.completeButton.text = "팔로잉"
            viewModel.selectAllFollowee.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })
        }


        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.selectAll.observe(viewLifecycleOwner, Observer {
            Log.e("selectAll","======================================================")
            Log.e("selectAll","${it}")
            Log.e("selectAll","======================================================")
        })

        return binding.root

    }

}