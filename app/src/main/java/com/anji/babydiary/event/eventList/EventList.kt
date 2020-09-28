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
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.event.EventDatabase
import com.anji.babydiary.databinding.EventListFragmentBinding
import com.anji.babydiary.event.eventList.listAdapter.EventListAdapter

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

        val adapter = EventListAdapter(EventListClickListener {

            it?.let {
                //viewModel.onClickImage(it)
                findNavController().navigate(EventListDirections.actionEventListToEventDetail(it))
            }
        },
        requireActivity())
        binding.eventList.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.selectAll.observe(viewLifecycleOwner, Observer {
            Log.e("select all ","data size: ${it.size}")
            if (it.size <= 0) {

                viewModel.insertData(
                    "img_1",
                    "100일 일기 챌린지",
                    "100일동안 우리아이 육아일기 쓰고 포토북 받자!\n" +
                            "100일 육아일기를 작성하면 추첨을 통해 ‘포토북’을 만들어드려요~\n" +
                            "우리아이의 성장을 기록하고 오래도록 추억으로 남길 수 있는 절호의 찬스! \n" +
                            "게시물작성 시, ‘#100일일기챌린지_0일’을 태그해주세요!\n" +
                            "\n" +
                            "참여방법 : 게시물 작성시 ‘#100일일기챌린지_0일’을 태그해주세요! (ex. #100일일기챌린지_3일)\n" +
                            "상품 : 100일 일기 챌린지를 달성하"
                )

                viewModel.insertData(
                    "img_2",
                    "말풍선을 채워주세요!",
                    "아빠가 육아를 하던 줄 아이가 갑자기 보채기 시작하더니 울음을 끊이지 않고 있어요!\n" +
                            "아이가 무슨 말을 하고 싶은 걸까요? \n" +
                            "\n" +
                            "- 참여방법 : 어떤 상황인지 자유롭게 유추해서 말풍선에 들어갈 아이의 말을 댓글로 남겨주세요!\n" +
                            "상품 : 추첨을 통해 10분에게 스*벅스 기프티콘을 드려요~\n" +
                            "이벤트기간 : ~ 9.30까지"
                )

                viewModel.insertData(
                    "img_3",
                    "키워드 이벤트",
                    "이번 키워드는 “꼬물”♥\n" +
                            "키워드를 연상시키는 사진을 올리고 이벤트를 태그하면 푸짐한 경품이 와르르!\n" +
                            "우리아이의 꼬물스런 장면을 포착해보세요!\n" +
                            "\n" +
                            "참여방법 : 키워드를 연상시키는 게시물을 업로드하고 ‘#키워드이벤트_꼬물’을 태그해주세요!\n" +
                            "상품 : 공감 수에 따라 상품을 드려요!\n" +
                            "1등(1명) >>> 영유아 용품 상품권 100,000원\n" +
                            "2등(3명) >>> 영유아 용품 상품권 50,000원\n" +
                            "3등(10명) >>> 스*벅스 아메리카노(ICE) 기프티콘\n" +
                            "- 이벤트기간 : ~12월 08일까지 "
                )
            }else {
                it?.let {
                    adapter.submitList(it)
                }
            }

        })

        binding.addEvent.setOnClickListener {
           // permissionCheck()
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