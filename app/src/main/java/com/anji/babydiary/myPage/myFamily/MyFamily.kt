package com.anji.babydiary.myPage.myFamily

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
import com.anji.babydiary.database.family.FamilyDao
import com.anji.babydiary.database.family.FamilyDatabase
import com.anji.babydiary.databinding.MyFamilyFragmentBinding
import com.anji.babydiary.myPage.myFamily.listAdapter.FamilyListAdapter

class MyFamily : Fragment() {


    private lateinit var viewModel: MyFamilyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<MyFamilyFragmentBinding>(inflater, R.layout.my_family_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = FamilyDatabase.getInstance(application).database

        val viewModelFactory = MyFamilyViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyFamilyViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.familyViewModel = viewModel


        var adapter = FamilyListAdapter()

        binding.familyList.adapter = adapter

        viewModel.selectAll.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

}