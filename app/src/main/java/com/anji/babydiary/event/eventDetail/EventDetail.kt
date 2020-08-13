package com.anji.babydiary.event.eventDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.anji.babydiary.R
import com.anji.babydiary.database.event.Event
import com.anji.babydiary.database.event.EventDatabase
import com.anji.babydiary.databinding.EventDetailFragmentBinding

class EventDetail : Fragment() {

    private lateinit var viewModel: EventDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<EventDetailFragmentBinding>(inflater, R.layout.event_detail_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = EventDatabase.getInstance(application).database

        val viewModelFactory = EventDetailViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventDetailViewModel::class.java)

        binding.eventViewModel = viewModel


        return binding.root
    }



}