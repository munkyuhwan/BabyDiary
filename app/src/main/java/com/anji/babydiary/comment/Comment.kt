package com.anji.babydiary.comment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.comment.commentListAdapter.CommentListAdapter
import com.anji.babydiary.database.comments.CommentsDatabase
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.databinding.CommentFragmentBinding
import com.anji.babydiary.myPage.profile.MyProfileViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class Comment : Fragment() {


    private lateinit var viewModel: CommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argment = arguments?.let { CommentArgs.fromBundle(it) }
        val idx = argment!!.feedIdx

        val binding = DataBindingUtil.inflate<CommentFragmentBinding>(inflater, R.layout.comment_fragment, container, false)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        val application = requireNotNull(this.activity).application
        val database = CommentsDatabase.getInstance(application).database
        val profileDatabase = ProfileDatabase.getInstance(application).database

        val viewModelFactory = CommentViewModelFactory(application, database, profileDatabase, idx)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CommentViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.commentViewModel = viewModel

        val adapter = CommentListAdapter(requireActivity(), viewLifecycleOwner)
        binding.commentList.adapter = adapter

        viewModel.totalList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.profile.observe(viewLifecycleOwner, Observer {
            it?.let {

                if(!it.imgTmp.equals("")) {
                    Glide.with(binding.root.context)
                        .load(requireActivity().resources.getIdentifier(it.imgTmp, "drawable", requireActivity().packageName))
                        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.commentUsrIcon)
                    binding.executePendingBindings()
                }else {
                    Glide.with(binding.root.context).load(it.img).into(binding.commentUsrIcon)
                    binding.executePendingBindings()
                }
            }
        })

        return binding.root

    }

}