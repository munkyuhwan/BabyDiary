package com.anji.babydiary.tips.writeTip

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import com.anji.babydiary.databinding.WriteTipFragmentBinding
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.shopping.TipDatabase
import com.anji.babydiary.tips.writeTip.tipCategorySpinner.TipCategoryAdapter
import com.bumptech.glide.Glide

class WriteTip : Fragment() {

    private lateinit var viewModel: WriteTipViewModel
    private lateinit var binding:WriteTipFragmentBinding
    private lateinit var application: Application
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<WriteTipFragmentBinding>(inflater, R.layout.write_tip_fragment, container, false)

        application = requireNotNull(this.activity).application
        val database = TipDatabase.getInstance(application).database

        val viewModelFactory = WriteTipViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WriteTipViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.tipWriteVM = viewModel

        binding.tipImgView.setOnClickListener {
            permissionCheck()
        }

        viewModel.selectedImage.observe(viewLifecycleOwner, Observer {
            it?.let { url ->
                Glide.with(application).load(url).into(binding.tipImgView)
            }
        })

        viewModel.isDone.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        })
        viewModel.categories.observe(viewLifecycleOwner, Observer{
            it?.let{
                val listItemsTxt = it

                var spinnerAdapter: TipCategoryAdapter = TipCategoryAdapter(application, listItemsTxt)
                binding.categorySpinner.adapter = spinnerAdapter

            }
        })

        /*
        binding.categorySpinner.setOnItemClickListener { adapterView, view, i, l ->
            Log.e("item select","==========================================")
            Log.e("item select","${i}, ${l}")
            Log.e("item select","==========================================")
            viewModel.onCategorySelected(i);
        }

         */

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //print("onItemSelected position = $position id = $id")
                viewModel.onCategorySelected(position);
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
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

