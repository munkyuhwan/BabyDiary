package com.anji.babydiary.myPage.chatting.chattingRoom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.database.chatting.ChattingDatabase
import com.anji.babydiary.databinding.ChattingRoomFragmentBinding

class ChattingRoom : Fragment() {

    private lateinit var viewModel: ChattingRoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        val arg = arguments?.let { ChattingRoomArgs.fromBundle(it) }
        val chatIdx = arg!!.userIdxOne

        val binding = DataBindingUtil.inflate<ChattingRoomFragmentBinding>(inflater, R.layout.chatting_room_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val database = ChattingDatabase.getInstance(application).database

        val viewModelFactory = ChattingRoomViewModelFactory(database, chatIdx, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChattingRoomViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.chatViewModel = viewModel

        viewModel.chatData(chatIdx)

        val adapter = ChattingRoomListAdapter(requireActivity(), viewLifecycleOwner)
        binding.chatText.adapter = adapter


        viewModel.chatData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                binding.chatText.scrollToPosition(binding.chatText!!.adapter!!.itemCount - 1)
            }
        })

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
            requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }

        return binding.root
    }



}