package com.anji.babydiary.tips.tipsList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.database.shopping.TipDatabase
import com.anji.babydiary.databinding.TipListFragmentBinding

class TipListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<TipListFragmentBinding>(inflater, R.layout.tip_list_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = TipDatabase.getInstance(application).database

        val viewModelFactory = TipListViewModelFactory(database, application)
        val viewmodel = ViewModelProviders.of(this, viewModelFactory).get(TipListViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.tipListViewModel = viewmodel

        binding.goWriteTip.setOnClickListener {
            findNavController().navigate(TipListFragmentDirections.actionTipListFragmentToWriteTip())
        }

        return binding.root
    }


}