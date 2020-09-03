package com.anji.babydiary.myPage.alarm

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.database.alarm.AlarmDatabase
import com.anji.babydiary.databinding.MyAlarmFragmentBinding

class MyAlarm : Fragment() {


    private lateinit var viewModel: MyAlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<MyAlarmFragmentBinding>(inflater,R.layout.my_alarm_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = AlarmDatabase.getInstance(application).database

        val viewModelFactory = MyAlarmViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyAlarmViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.alarmViewModel = viewModel

        val adapter = AlarmListAdapter()
        binding.alarmList.adapter = adapter

        viewModel.selectAll.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }



        return binding.root
    }


}