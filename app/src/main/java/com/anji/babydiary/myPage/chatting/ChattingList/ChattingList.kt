package com.anji.babydiary.myPage.chatting.chattingList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.database.chatting.ChattingDatabase
import com.anji.babydiary.databinding.ChattingListFragmentBinding
import com.anji.babydiary.myPage.chatting.ChattingList.chattingListAdapter.ChattingListAdapter
import com.anji.babydiary.myPage.myFeed.MyFeedDirections

class ChattingList : Fragment() {

    companion object {
        fun newInstance() = ChattingList()
    }

    private lateinit var viewModel: ChattingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<ChattingListFragmentBinding>(inflater, R.layout.chatting_list_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = ChattingDatabase.getInstance(application).database

        val viewModelFactory = ChattingListViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChattingListViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.chattingViewModel = viewModel


        val adapter = ChattingListAdapter( OnChattingListClick {
            findNavController().navigate(ChattingListDirections.actionChattingListToChattingRoom2(it) )
        } )
        binding.msgList.adapter = adapter

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
            requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }

        viewModel.allData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


        return binding.root
    }


}