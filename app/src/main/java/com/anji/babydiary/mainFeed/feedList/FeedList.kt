package com.anji.babydiary.mainFeed.feedList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.BaseFragment
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.bookmark.BookMarkDatabase
import com.anji.babydiary.database.likes.LikesDatabase
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.databinding.FeedListFragmentBinding
import com.anji.babydiary.login.Login
import com.anji.babydiary.mainFeed.feedList.listAdapter.MainFeedListAdapter
import com.anji.babydiary.myPage.MyPage
import com.anji.babydiary.myPage.myFeed.MyFeed
import com.anji.babydiary.search.SearchActivity

class FeedList : BaseFragment() {

    private lateinit var viewModel: FeedListViewModel
    private lateinit var viewModelFactory: FeedListViewModelFactory
    //private lateinit var binding:FeedListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = DataBindingUtil.inflate<FeedListFragmentBinding>(inflater, R.layout.feed_list_fragment, container, false)
        //viewModel = ViewModelProviders.of(this).get(FeedListViewModel::class.java)

        val application = requireNotNull(this.activity).application
        val dataSource = MainFeedDatabase.getInstance(application).database
        val profileData = ProfileDatabase.getInstance(application).database
        val likeDatabase = LikesDatabase.getInstance(application).database
        val bookMark = BookMarkDatabase.getInstance(application).database


        viewModelFactory = FeedListViewModelFactory(dataSource, profileData, likeDatabase, bookMark, requireActivity(), application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedListViewModel::class.java)
        viewModel.selectAll()

        binding.mainFeed = viewModel
        binding.setLifecycleOwner(this)

        var navOption = NavOptions.Builder().setEnterAnim(R.anim.fade_in).setExitAnim(R.anim.fade_out).build()

        val adapter = MainFeedListAdapter(
            FeedClickListener {
                findNavController().navigate(FeedListDirections.actionFeedListToFeedDetail(it), navOption)
            },
            FeedCommentClickListener {
                it?.let {
                    findNavController().navigate(FeedListDirections.actionFeedListToComment(it), navOption)
                }
            },
            MemberClickListener {
                it?.let {
                    var intent = Intent(activity, MyPage::class.java)
                    intent.putExtra("userIdx",it)
                    requireActivity().startActivity(intent)
                }
            },
            BookMarkClickListener {
                //Toast.makeText(requireContext(), "${it}", Toast.LENGTH_SHORT).show()
                viewModel.insertBookMark(it)
            },
            viewModel,
        requireActivity(), viewLifecycleOwner)
        binding.feedList.adapter = adapter

        viewModel.allFeeds.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.isCategoryOpen.observe(viewLifecycleOwner, Observer {
            Log.e("isCateogry", "is category open: ${it}")
            it?.let {
                /*
                if (it) {
                    binding.navCategory.visibility = View.VISIBLE
                }else {
                    binding.navCategory.visibility = View.GONE
                }

                 */
            }
        })

        binding.appCompatImageView.setOnClickListener {
            val intent: Intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivityForResult(intent, CommonCode.SEARCH_KEYWORD)
        }

        viewModel.bookMarks.observe(viewLifecycleOwner, Observer {
            it?.let {
                for (i in it.iterator()) {
                    Log.e("bookmars", "=======================================================")
                    Log.e("bookmars", "${i.feed_idx}")
                    Log.e("bookmars", "=======================================================")

                    viewModel.selectBookmarkedFeed(i.feed_idx)
                }
            }
        })

        //checkProfile(application)

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CommonCode.SEARCH_KEYWORD && resultCode == Activity.RESULT_OK) {

            data?.let {

                it.extras?.let {
                    viewModel.selectByKeyword(it.get("keyword").toString())
                }
            }

        }

    }


}