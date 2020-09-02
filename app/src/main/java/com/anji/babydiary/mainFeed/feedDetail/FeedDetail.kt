package com.anji.babydiary.mainFeed.feedDetail

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.bottomActivity.BottomMenu
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.likes.LikesDatabase
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.databinding.FeedDetailFragmentBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.nav_layout.*

class FeedDetail : Fragment() {

    private lateinit var viewModel: FeedDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argment = arguments?.let { FeedDetailArgs.fromBundle(it) }

        val idx = argment!!.feedIdx

        val binding = DataBindingUtil.inflate<FeedDetailFragmentBinding>(inflater, R.layout.feed_detail_fragment, container, false)

        val application = requireNotNull(activity).application
        val database = MainFeedDatabase.getInstance(application).database
        val likeDatabase = LikesDatabase.getInstance(application).database
        val profileDatabase = ProfileDatabase.getInstance(application).database

        val viewModelFactory = MyFeedDetailViewModelFactory(idx, database, likeDatabase, profileDatabase)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedDetailViewModel::class.java)

        binding.feedViewModel = viewModel

        viewModel.select.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.writtenDate.text = Utils.getDate(it.date, "YYYY. MM. dd")
                binding.feedText.text = it.title
                binding.toSpouser.text = it.toSpouser
                binding.babyWeight.text = it.weight.toString()
                binding.babyHeight.text = it.height.toString()
                Glide.with(binding.root.context)
                    .load(it.imgDir)
                    .into(binding.myFeedImage)
            }
        })

        viewModel.likeCount.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.count()
                binding.vibe.text = "공감 ${it.count()}개"
            }
        })

        binding.commentBtn.setOnClickListener {
            findNavController().navigate(FeedDetailDirections.actionFeedDetailToComment(idx) )
        }

        viewModel.writerProfile.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.nameKid.text = it.name
            }
        })


        binding.moreMenuBtn.setOnClickListener{
            //val intent: Intent = Intent(activity, BottomMenu::class.java)
            //intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            //startActivityForResult(intent, CommonCode.MYPAGE_ACTIVITY_RESULT)
        }


        binding.dailycheckBtn.setOnClickListener {
            it?.let {

                Log.e("writer","writer idx: ${viewModel.writerProfile.value!!.idx}")
                Log.e("writer","My idx: ${CommonCode.USER_IDX}")

                findNavController().navigate(FeedDetailDirections.actionFeedDetailToChattingRoom2( viewModel.writerProfile.value!!.idx ) )

            }
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }



}