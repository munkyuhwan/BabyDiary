package com.anji.babydiary.comment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.anji.babydiary.R
import com.anji.babydiary.comment.commentListAdapter.CommentListAdapter
import com.anji.babydiary.database.comments.CommentsDatabase
import com.anji.babydiary.databinding.CommentFragmentBinding

class Comment : Fragment() {


    private lateinit var viewModel: CommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val argment = arguments?.let { CommentArgs.fromBundle(it) }
        val idx = argment!!.feedIdx

        val binding = DataBindingUtil.inflate<CommentFragmentBinding>(inflater, R.layout.comment_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = CommentsDatabase.getInstance(application).database

        val viewModelFactory = CommentViewModelFactory(application, database, idx)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CommentViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.commentViewModel = viewModel

        val adapter = CommentListAdapter()
        binding.commentList.adapter = adapter

        viewModel.totalList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

}