package com.anji.babydiary.tips.tipsList

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.tip.TipsDatabase
import com.anji.babydiary.database.tip.tipsBookmark.TipBookMarkDatabase
import com.anji.babydiary.databinding.TipListFragmentBinding
import com.anji.babydiary.myPage.MyPage
import com.anji.babydiary.search.SearchActivity
import com.anji.babydiary.tips.tipsList.listAdapter.TipListAdpater

class TipListFragment : Fragment() {


    lateinit var viewmodel:TipListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<TipListFragmentBinding>(inflater, R.layout.tip_list_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = TipsDatabase.getInstance(application).database
        val bookMarkDatabase = TipBookMarkDatabase.getInstance(application).database

        val viewModelFactory = TipListViewModelFactory(database, bookMarkDatabase, MyShare.prefs.getLong("idx", 0L), application)
        viewmodel = ViewModelProviders.of(this, viewModelFactory).get(TipListViewModel::class.java)

        val adapter =
            TipListAdpater(TipClickListener {

            },
                TipLikeClicked{ resultId: Long, cnt: CharSequence ->
                    viewmodel.insertLike(resultId, cnt.toString().toInt()+1 )
                }
                ,
                TipCommentClicked {
                    findNavController().navigate(TipListFragmentDirections.actionTipListFragmentToTipsComment(it))
                },
                TipUserClicked {
                    it?.let {
                        var intent = Intent(activity, MyPage::class.java)
                        intent.putExtra("userIdx",it)
                        requireActivity().startActivity(intent)
                    }
                },
                TipBookMarkClickListener {
                    viewmodel.selectBookmark(it)
                },
            requireActivity(), viewLifecycleOwner)
        binding.tipList.adapter = adapter

        binding.lifecycleOwner = viewLifecycleOwner
        binding.tipListViewModel = viewmodel

        binding.goWriteTip.setOnClickListener {
            findNavController().navigate(TipListFragmentDirections.actionTipListFragmentToWriteTip())
        }

        viewmodel.dataAll.observe(viewLifecycleOwner, Observer {
            Log.e("data","===========================================================================")
            Log.e("data","${it}")
            Log.e("data","===========================================================================")
            it?.let {
                viewmodel.isCategoryOpen.value = View.GONE
                adapter.submitList(it)
            }
        })

        binding.searchHospital.setOnClickListener {
            findNavController().navigate(TipListFragmentDirections.actionTipListFragmentToMapWebview())
        }

        binding.appCompatImageView.setOnClickListener {
            val intent: Intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivityForResult(intent, CommonCode.SEARCH_KEYWORD)
        }

        viewmodel.seletBookMark.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size <= 0) {
                    viewmodel.addBookmark(viewmodel.bookmarkTipIdx)
                }else {
                    viewmodel.deleteBookmark(viewmodel.bookmarkTipIdx)
                }

            }
        })

        viewmodel.bookMarks.observe(viewLifecycleOwner, Observer {
            it?.let {
                for (i in it.iterator()) {
                    viewmodel.selectBookmarkedFeed(i.tipIdx)
                }
            }
        })


        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CommonCode.SEARCH_KEYWORD && resultCode == Activity.RESULT_OK) {

            data?.let {

                it.extras?.let {
                    viewmodel.selectSearch(it.get("keyword").toString())
                }
            }

        }

    }



}