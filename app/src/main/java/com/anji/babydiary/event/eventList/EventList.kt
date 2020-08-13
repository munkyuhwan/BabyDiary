package com.anji.babydiary.event.eventList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.anji.babydiary.R
import com.anji.babydiary.database.event.EventDao
import com.anji.babydiary.database.event.EventDatabase
import com.anji.babydiary.databinding.EventListFragmentBinding

class EventList : Fragment() {

    private lateinit var viewModel: EventListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<EventListFragmentBinding>(inflater, R.layout.event_list_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = EventDatabase.getInstance(application).database

        val viewModelFactory = EventListViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventListViewModel::class.java)


        return binding.root
    }


}