package com.anji.babydiary.shopping.writeFragment

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
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.OpenGallery
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.shopping.ShoppingDatabase
import com.anji.babydiary.databinding.WriteProductFragmentBinding
import com.anji.babydiary.shopping.listFragment.ShopListFragmentDirections
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main_feed.*


class WriteProduct : Fragment() {

    private val PERMISSION_CODE = 9898
    private val IMAGE_PICK_CODE = 1000;

    private lateinit var viewModel: WriteProductViewModel
    private lateinit var viewModelFactory:WriteProductViewModelFactory
    private lateinit var binding:WriteProductFragmentBinding

    private lateinit var application:Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().fab.visibility = View.GONE

        binding = DataBindingUtil.inflate(inflater, R.layout.write_product_fragment, container, false)

        application = requireNotNull(this.activity).application
        val database = ShoppingDatabase.getInstance(application).database

        viewModelFactory = WriteProductViewModelFactory(database, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WriteProductViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.writeViewModel = viewModel

        binding.imageView.setOnClickListener {
            openGallery()
        }

        viewModel.selectedImage.observe(viewLifecycleOwner, Observer {
            it?.let { url ->
                Glide.with(application).load(url).into(binding.imageView)
            }
        })

        viewModel.isValid.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it) {
                    showToast(viewModel.invalidString)
                }
            }
        })

        viewModel.isDone.observe(viewLifecycleOwner, Observer {
            it?.let{
                if(it) {
                    findNavController().popBackStack()
                }
            }
        })

        binding.backBtn.setOnClickListener {
            Utils.showAlert(requireContext(),"알림","제품작성을 취소하시겠습니까?\n작성중인 글은 저장되지 않습니다.", findNavController())
        }

        return binding.root
    }


    fun openGallery() {
        //activity?.let {
        //    OpenGallery.getInstance().permissionCheck(this.application, it)
        // }
        permissionCheck()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE) {

            if (data != null) {
                viewModel.onImageSelected(data)
            }

        }

    }


    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    fun permissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(application,Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
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

    fun showToast(column:String) {
        Toast.makeText(this.requireContext(), "${column}을(를) 입력 해 주세요", Toast.LENGTH_SHORT).show()
    }

}