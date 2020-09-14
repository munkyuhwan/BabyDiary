package com.anji.babydiary.mainFeed.feedDetail

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
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.likes.LikesDatabase
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.databinding.FeedDetailFragmentBinding
import com.bumptech.glide.Glide

class MyFeedDetail : Fragment() {

    private lateinit var viewModel: FeedDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argment = arguments?.let { MyFeedDetailArgs.fromBundle(it) }

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
               // viewModel.getWriterProfile()
               // binding.writtenDate.text = Utils.getDate(it.timeMilli, "YYYY. MM. dd")
                binding.writtenDate.text = "${it.year} ${String.format("%02d",it.month)} ${String.format("%02d", it.date)}"
                binding.feedText.text = it.title
                binding.toSpouser.text = it.toSpouser
                binding.babyWeight.text = it.weight.toString()
                binding.babyHeight.text = it.height.toString()
                if (it.imgTmpDir != "") {
                    Glide.with(binding.root.context)
                        .load(requireActivity().resources.getIdentifier(it.imgTmpDir, "drawable", requireActivity().packageName))
                        .into(binding.myFeedImage)
                }else {
                    Glide.with(binding.root.context)
                        .load(it.imgDir)
                        .into(binding.myFeedImage)
                }
            }
        })

        viewModel.likeCount.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.count()
                binding.vibe.text = "공감 ${it.count()}개"
            }
        })

        binding.commentBtn.setOnClickListener {
            findNavController().navigate(MyFeedDetailDirections.actionMyFeedDetailToComment3(idx) )
        }

        binding.dailycheckBtn.setOnClickListener {

        }

        return binding.root
    }

}