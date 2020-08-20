package com.anji.babydiary.myPage.myFollower

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.anji.babydiary.R
import com.anji.babydiary.database.follow.FollowDatabase
import com.anji.babydiary.databinding.FollowerFragmentBinding

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


        return binding.root

    }

}