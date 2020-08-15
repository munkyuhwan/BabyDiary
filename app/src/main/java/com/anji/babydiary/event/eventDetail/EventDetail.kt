package com.anji.babydiary.event.eventDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.anji.babydiary.R
import com.anji.babydiary.database.event.Event
import com.anji.babydiary.database.event.EventDatabase
import com.anji.babydiary.databinding.EventDetailFragmentBinding
import com.bumptech.glide.Glide

class EventDetail : Fragment() {

    private lateinit var viewModel: EventDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val argument = arguments?.let { EventDetailArgs.fromBundle(it) }

        val binding = DataBindingUtil.inflate<EventDetailFragmentBinding>(inflater, R.layout.event_detail_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = EventDatabase.getInstance(application).database

        val viewModelFactory = EventDetailViewModelFactory(database, argument!!.eventIdx)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventDetailViewModel::class.java)

        binding.eventViewModel = viewModel


        viewModel.eventDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                Glide.with(binding.root.context).load(it.imgDir).into(binding.eventDetailImage)
                binding.eventDetailTitle.text = it.title.toString()
                binding.eventDetailText.text = it.text.toString()
            }
        })



        return binding.root
    }



}