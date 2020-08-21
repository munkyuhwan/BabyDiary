package com.anji.babydiary.myPage.myFollower

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.anji.babydiary.R
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

        val viewModelFactory = FollowerViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FollowerViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.followerViewModel = viewModel

        val adapter = FollowListAdapter()

        binding.followList.adapter = adapter

        val argument = arguments?.let { FollowerArgs.fromBundle(it) }


        if (argument!!.equals("follower")) {
            viewModel.selectAllFollowee.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })
        }

        if (argument!!.equals("followeee")) {
            viewModel.selectAllFollower.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })
        }



        return binding.root

    }

}