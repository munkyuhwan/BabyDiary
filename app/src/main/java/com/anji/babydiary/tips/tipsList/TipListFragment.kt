package com.anji.babydiary.tips.tipsList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.shopping.TipsDatabase
import com.anji.babydiary.databinding.TipListFragmentBinding
import com.anji.babydiary.tips.tipsList.listAdapter.TipListAdpater

class TipListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<TipListFragmentBinding>(inflater, R.layout.tip_list_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = TipsDatabase.getInstance(application).database

        val viewModelFactory = TipListViewModelFactory(database, application)
        val viewmodel = ViewModelProviders.of(this, viewModelFactory).get(TipListViewModel::class.java)

        val adapter = TipListAdpater(TipClickListener {

        })
        binding.tipList.adapter = adapter

        binding.lifecycleOwner = viewLifecycleOwner
        binding.tipListViewModel = viewmodel

        binding.goWriteTip.setOnClickListener {
            findNavController().navigate(TipListFragmentDirections.actionTipListFragmentToWriteTip())
        }

        viewmodel.dataAll.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.e("data","data:${it}")
                adapter.submitList(it)
            }
        })

        /*
        viewmodel.dataWithProfile.observe(viewLifecycleOwner, Observer {
            it?.let{
                Log.e("dataProfile","============================================================")
                Log.e("dataProfile","dataProfile: ${it}")
                Log.e("dataProfile","============================================================")
            }
        })

         */

        return binding.root
    }


}