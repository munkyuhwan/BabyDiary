package com.anji.babydiary.tips.tipsComment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anji.babydiary.R
import com.anji.babydiary.database.tip.tipsComment.TipsCommentDatabase
import com.anji.babydiary.databinding.TipsCommentFragmentBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class TipsComment : Fragment() {


    private lateinit var viewModel: TipsCommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argment = arguments?.let { TipsCommentArgs.fromBundle(it) }
        val idx = argment!!.tipIdx

        val binding = DataBindingUtil.inflate<TipsCommentFragmentBinding>(inflater, R.layout.tips_comment_fragment, container, false)

        val application = requireNotNull(activity).application
        val database = TipsCommentDatabase.getInstance(application).database

        val vmFactory = TipsCommentViewModelFactory(database, idx, application)
        viewModel = ViewModelProviders.of(this, vmFactory).get(TipsCommentViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel


        val adapter = TipsCommentListAdapter(requireActivity())
        binding.commentList.adapter = adapter

        viewModel.dataList.observe(viewLifecycleOwner, Observer {
            Log.e("comments","======================================================================")
            Log.e("comments","${it}")
            Log.e("comments","======================================================================")
            it?.let {
                adapter.submitList(it)
            }

        })

        viewModel.dataProfile.observe(viewLifecycleOwner, Observer {
            if (it!!.imgTmp != "") {
                Glide.with(binding.root.context)
                    .load(resources.getIdentifier(it.imgTmp, "drawable", requireActivity().packageName))
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.commentUsrIcon)
            }else {
                Glide.with(binding.root.context)
                    .load(it.img)
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.commentUsrIcon)
            }

        })



        return binding.root
    }


}