package com.anji.babydiary.myPage.myFeedWrite

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.databinding.MyFeedWriteFragmentBinding
import com.anji.babydiary.myPage.myFeed.MyFeedDirections
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.nav_mypage_layout.*

class MyFeedWrite : Fragment() {

    private lateinit var viewModel: MyFeedWriteViewModel
    private lateinit var binding:MyFeedWriteFragmentBinding
    private lateinit var application:Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<MyFeedWriteFragmentBinding>(inflater, R.layout.my_feed_write_fragment, container, false)

        application = requireNotNull(this.activity).application
        val database = MainFeedDatabase.getInstance(application).database

        val viewModelFactory = MyFeedWriteViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyFeedWriteViewModel::class.java)

        binding.writeViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.openKidInfo.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                viewModel.isShown.value = View.VISIBLE
            }else {
                viewModel.isShown.value = View.GONE
            }
        }
        binding.babyDetailLayout.setOnClickListener {
            binding.openKidInfo.performClick()
        }

        binding.tolocationLayer.setOnClickListener {
            findNavController().navigate(MyFeedWriteDirections.actionMyFeedWriteToMyFeedWriteLocation())
        }



        binding.completeButton.setOnClickListener {
            viewModel.complete(binding.titleInput.text.toString(),  binding.heightInputEditText.text.toString(), binding.weightInputEditText.text.toString(), binding.headInputEditText.text.toString(), binding.selectedAddress.text.toString(), binding.toWife.text.toString() )
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }



        binding.myFeedImage.setOnClickListener{
            permissionCheck()
        }
        binding.addBtn.setOnClickListener{
            permissionCheck()
        }

        viewModel.isDone.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    findNavController().popBackStack()
                }
            }
        })

        viewModel.selectedImage.observe(viewLifecycleOwner, Observer {
            it?.let { url ->
                Glide.with(application).load(url).into(binding.myFeedImage)
            }
        })

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