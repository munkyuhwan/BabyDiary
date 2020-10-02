package com.anji.babydiary.myPage.myFeedWrite

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
import com.anji.babydiary.tips.writeTip.tipCategorySpinner.TipCategoryAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main_feed.*
import kotlinx.android.synthetic.main.my_feed_write_fragment.view.*


class MyFeedWrite : Fragment(), View.OnTouchListener {

    private lateinit var viewModel: MyFeedWriteViewModel
    private lateinit var binding:MyFeedWriteFragmentBinding
    private lateinit var application:Application

    var finalScaleX = 0F
    var finalScaleY = 0F
    var finalScaleFactor = 0F

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().fab.visibility = View.GONE

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
            binding.testTextBoxLayer.background = ContextCompat.getDrawable(requireContext(),R.drawable.actionbar_transparent)

            requireActivity().getExternalFilesDir("${Environment.DIRECTORY_DCIM}/babyDiary")

            val bitmap = Utils.getScreenShotFromView(binding.myFeedImageLayout)
            viewModel.selectedImage.value = Utils.saveBitmapToJpeg(bitmap!!, "${System.currentTimeMillis()}", requireActivity().getExternalFilesDir("${Environment.DIRECTORY_DCIM}/babyDiary")!! )
            //Glide.with(application).load(bitmap).into(binding.myFeedImage)

            viewModel.complete(binding.titleInput.text.toString(),  binding.heightInputEditText.text.toString(), binding.weightInputEditText.text.toString(), binding.headInputEditText.text.toString(), binding.selectedAddress.text.toString(), binding.toWife.text.toString() )
        }

        binding.backBtn.setOnClickListener {
            Utils.showAlert(requireContext(),"알림","피드작성을 취소하시겠습니까?\n작성중인 피드는 저장되지 않습니다.", findNavController())
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


         binding.myFeedImage.setOnScaleChangeListener { scaleFactor, focusX, focusY ->
             finalScaleX = focusX
             finalScaleY = focusY
             finalScaleFactor = scaleFactor
         }


        viewModel.selectedImage.observe(viewLifecycleOwner, Observer {
            it?.let { url ->
                Glide.with(application).load(url).into(binding.myFeedImage)
               // binding.myFeedImage.setImageURI(url)
            }
        })

        binding.addTextBox.setOnClickListener {

        }

        binding.testTextBoxLayer.setOnTouchListener(this)
        //binding.testTextBox.setOnTouchListener(this)

        binding.textInsertField.doOnTextChanged { text, start, before, count ->
            binding.testTextBox.text = text
        }

        var spinnerAdapter: MyFeedSpinner = MyFeedSpinner(application, viewModel.textSizes)
        binding.textSizeSel.adapter = spinnerAdapter
        binding.textSizeSel.setSelection(10)

        var colorSpinner:MyFeedColorSpinner = MyFeedColorSpinner(application, viewModel.textColor)
        binding.textColorSel.adapter = colorSpinner
        binding.textColorSel.setSelection(0)

        binding.textColorSel.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.e("selected color", "${viewModel.textColor[position]}")
                binding.textColorSel.setBackgroundColor(Color.parseColor(viewModel.textColor[position]))
                binding.testTextBox.setTextColor(Color.parseColor(viewModel.textColor[position]))
            }

        }

        binding.textSizeSel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //print("onItemSelected position = $position id = $id")
                Log.e("selectedSize", "${viewModel.textSizes[position]}")
                binding.testTextBox.textSize = viewModel.textSizes[position].toFloat()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        binding.isBold.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.testTextBox.setTypeface(null, Typeface.BOLD)
            }else {
                binding.testTextBox.setTypeface(null, Typeface.NORMAL)
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