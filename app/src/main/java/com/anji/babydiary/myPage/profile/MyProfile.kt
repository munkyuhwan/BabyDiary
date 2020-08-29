package com.anji.babydiary.myPage.profile

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
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.OpenGallery
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.databinding.ProfileFragmentBinding
import com.bumptech.glide.Glide

class MyProfile : Fragment() {


    private lateinit var viewModel: MyProfileViewModel
    private lateinit var application:Application
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<ProfileFragmentBinding>(inflater, R.layout.profile_fragment, container, false)
        val idx:Long = CommonCode.USER_IDX

        application = requireNotNull(this.activity).application
        val database = ProfileDatabase.getInstance(application).database

        val viewModelFactory = MyProfileViewModelFactory(idx, database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyProfileViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.profileViewModel = viewModel

        binding.editBtn.setOnClickListener {
            permissionCheck()
        }

        viewModel.selectedImage.observe(viewLifecycleOwner, Observer {
            it?.let { url ->
                Glide.with(application).load(url).into(binding.profileImg)
            }
        })

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                Glide.with(application).load(it.img).into(binding.profileImg)
                binding.profileName.setText(it.name)
                binding.profilePass.setText(it.pass)
                binding.profilePassCheck.setText(it.pass)
                binding.profileIntroduce.setText(it.introduce)
            }
        })
        viewModel.isDone.observe(viewLifecycleOwner, Observer {
            it?.let{

                if(it) {
                    Toast.makeText(context, "수정되었습니다.",Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
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