package com.anji.babydiary.myPage.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.anji.babydiary.R
import com.anji.babydiary.database.profile.Profile
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.databinding.ProfileFragmentBinding

class MyProfile : Fragment() {


    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<ProfileFragmentBinding>(inflater, R.layout.profile_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = ProfileDatabase.getInstance(application).database

        val viewModelFactory = MyProfileViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyProfileViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.profileViewModel = viewModel



        return binding.root
    }

}