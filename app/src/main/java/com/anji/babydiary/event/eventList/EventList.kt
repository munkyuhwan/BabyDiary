package com.anji.babydiary.event.eventList

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.event.EventDatabase
import com.anji.babydiary.databinding.EventListFragmentBinding
import com.anji.babydiary.event.eventList.listAdapter.EventListAdapter
import com.anji.babydiary.event.eventList.listAdapter.EventListClickListener

class EventList : Fragment() {

    private lateinit var viewModel: EventListViewModel
    private lateinit var application:Application
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<EventListFragmentBinding>(inflater, R.layout.event_list_fragment, container, false)

        application = requireNotNull(this.activity).application
        val database = EventDatabase.getInstance(application).database

        val viewModelFactory = EventListViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventListViewModel::class.java)

        binding.eventListViewModel = viewModel

        var adapter = EventListAdapter(EventListClickListener {
            //Toast.makeText(context, "${it}", Toast.LENGTH_SHORT).show()
        })
        binding.eventList.adapter = adapter



        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.selectAll.observe(viewLifecycleOwner, Observer {
            Log.e("selectAll","======================================================")
            Log.e("selectAll","${it}")
            Log.e("selectAll","======================================================")

            it?.let{
                adapter.submitList(it)
            }
        })

        binding.addEvent.setOnClickListener {
            permissionCheck()
        }

        return binding.root
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CommonCode.IMAGE_PICK_CODE) {

            if (data != null) {
                viewModel.onImageSelected(data)
            }

        }

    }


    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, CommonCode.IMAGE_PICK_CODE)
    }

    fun permissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(
                    application,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, CommonCode.PERMISSION_CODE);
            }
            else{
                //permission already granted
                pickImageFromGallery();
            }
        }
        else{
            //system OS is < Marshmallow
            pickImageFromGallery();
        }
    }

}