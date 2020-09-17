package com.anji.babydiary.myPage.myFeedWrite

import android.Manifest
import android.app.Application
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.databinding.MyFeedWriteFragmentBinding
import com.bumptech.glide.Glide

class MyFeedWrite : Fragment(), View.OnTouchListener {

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
           // permissionCheckForCapture()

            val bitmap = Utils.getScreenShotFromView(binding.myFeedImageLayout)
            Glide.with(application).load(bitmap).into(binding.myFeedImage)
            //viewModel.complete(binding.titleInput.text.toString(),  binding.heightInputEditText.text.toString(), binding.weightInputEditText.text.toString(), binding.headInputEditText.text.toString(), binding.selectedAddress.text.toString(), binding.toWife.text.toString() )
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }



        binding.myFeedImage.setOnClickListener{
            //permissionCheck()
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

        binding.addTextBox.setOnClickListener {

        }


        binding.testTextBox.setOnTouchListener(this)


        binding.textInsertField.doOnTextChanged { text, start, before, count ->
            binding.testTextBox.text = text
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
    fun permissionCheckForCapture() {
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
                val savedURI = Utils.saveBitmap( Utils.getScreenShotFromView(binding.myFeedImageLayout),  requireActivity().contentResolver)
                Log.e("savedURI", "savedURI: ${savedURI}")
            }
        }
        else{
            //system OS is < Marshmallow
            val savedURI = Utils.saveBitmap( Utils.getScreenShotFromView(binding.myFeedImageLayout),  requireActivity().contentResolver)
            Log.e("savedURI", "savedURI: ${savedURI}")
        }
    }

    var oldXvalue = 0f
    var oldYvalue = 0f
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val width = (binding.myFeedImageLayout as ViewGroup).width
        val height = (binding.myFeedImageLayout as ViewGroup).height


        if (event!!.action === MotionEvent.ACTION_DOWN) {
            oldXvalue = event!!.x
            oldYvalue = event.y
            //  Log.i("Tag1", "Action Down X" + event.getX() + "," + event.getY());
            Log.i(
                "Tag1",
                "Action Down rX " + event.rawX.toString() + "," + event.rawY
            )
        } else if (event!!.action === MotionEvent.ACTION_MOVE) {
            v!!.x = event!!.rawX - v!!.width/2
            v.y = event.rawY - (oldYvalue + v.height+(v.height/2))
            //  Log.i("Tag2", "Action Down " + me.getRawX() + "," + me.getRawY());
        } else if (event!!.action === MotionEvent.ACTION_UP) {
            if (v!!.x > width && v.y > height) {
                v.x = width.toFloat()
                v.y = height.toFloat()
            } else if (v.x < 0 && v.y > height) {
                v.setX(0f)
                v.y = height.toFloat()
            } else if (v.x > width && v.y < 0) {
                v.x = width.toFloat()
                v.setY(0f)
            } else if (v.x < 0 && v.y < 0) {
                v.setX(0f)
                v.setY(0f)
            } else if (v.x < 0 || v.x > width) {
                if (v.x < 0) {
                    v.setX(0f)
                    v.y = event!!.rawY - oldYvalue - v.height
                } else {
                    v.x = width.toFloat()
                    v.y = event!!.rawY - oldYvalue - v.height
                }
            } else if (v.y < 0 || v.y > height) {
                if (v.y < 0) {
                    v.x = event!!.rawX - oldXvalue
                    v.setY(0f)
                } else {
                    v.x = event!!.rawX - oldXvalue
                    v.y = height.toFloat()
                }
            }
        }
        return true
    }


}