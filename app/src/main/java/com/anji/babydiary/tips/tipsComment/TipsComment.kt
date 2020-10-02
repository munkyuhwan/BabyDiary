package com.anji.babydiary.tips.tipsComment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.anji.babydiary.R
import com.anji.babydiary.common.itemAction.ItemTouchHelperCallback
import com.anji.babydiary.database.tip.tipsComment.TipsCommentDatabase
import com.anji.babydiary.databinding.TipsCommentFragmentBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main_feed.*
import kotlinx.android.synthetic.main.comment_list_item.view.*


class TipsComment : Fragment() {

    private lateinit var viewModel: TipsCommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().fab.visibility = View.GONE

        val argment = arguments?.let { TipsCommentArgs.fromBundle(it) }
        val idx = argment!!.tipIdx

        val binding = DataBindingUtil.inflate<TipsCommentFragmentBinding>(inflater, R.layout.tips_comment_fragment, container, false)

        val application = requireNotNull(activity).application
        val database = TipsCommentDatabase.getInstance(application).database

        val vmFactory = TipsCommentViewModelFactory(database, idx, application)
        viewModel = ViewModelProviders.of(this, vmFactory).get(TipsCommentViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel


        val adapter = TipsCommentListAdapter(
            requireActivity(),
            viewLifecycleOwner,
            TipEditClicked(editClickListener = {resultId, newText ->
                viewModel.updateTipComment(resultId, newText.toString())
            }),
            TipDeleteClicked {
                viewModel.deleteTipCOmment(it)
            }
        )

        binding.commentList.adapter = adapter

        val simpleCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //mList.remove(viewHolder.layoutPosition)
                //Toast.makeText(requireContext(),"${viewHolder.layoutPosition}", Toast.LENGTH_SHORT).show()
                //viewModel.deleteTipCOmment(viewModel.dataList.value!!.get(viewHolder.layoutPosition).idx)
                if (direction == ItemTouchHelper.LEFT) {
                    viewHolder.itemView.editLayer.visibility = View.VISIBLE
                }else {
                    viewHolder.itemView.editLayer.visibility = View.GONE
                }
                viewHolder.itemView.deleteBtn.setOnClickListener {
                    viewModel.deleteTipCOmment(viewModel.dataList.value!!.get(viewHolder.layoutPosition).idx)
                }
                adapter.notifyItemRemoved(viewHolder.layoutPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.commentList)

        viewModel.dataList.observe(viewLifecycleOwner, Observer {
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

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }



}


