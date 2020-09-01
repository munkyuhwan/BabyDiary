package com.anji.babydiary.myPage.chatting.ChattingList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.anji.babydiary.R
import com.anji.babydiary.database.chatting.ChattingDatabase
import com.anji.babydiary.databinding.ChattingFragmentBinding

class ChattingList : Fragment() {


    private lateinit var viewModel: ChattingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<ChattingFragmentBinding>(inflater, R.layout.chatting_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = ChattingDatabase.getInstance(application).database

        val viewModelFactory = ChattingListViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChattingListViewModel::class.java)

        binding.chattingViewModel = viewModel



        return binding.root
    }

}

