package com.anji.babydiary.myPage.myFeed

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
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.anji.babydiary.R
import com.anji.babydiary.bottomActivity.BottomMenu
import com.anji.babydiary.bottomActivity.resign.Resign
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.follow.Follow
import com.anji.babydiary.database.follow.FollowDatabase
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.databinding.MyFeedFragmentBinding
import com.anji.babydiary.login.Login
import com.anji.babydiary.mainFeed.feedDetail.FeedDetailArgs
import com.anji.babydiary.myPage.myFeed.myFeedListAdapter.MyFeedListAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.nav_mypage_layout.view.*

class MyFeed : Fragment() {


    private lateinit var viewModel: MyFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var idx = CommonCode.USER_IDX

        val intent = requireActivity().intent.extras
        intent?.let {
            idx = it.getLong("userIdx")
        }

        val binding = DataBindingUtil.inflate<MyFeedFragmentBinding>(inflater, R.layout.my_feed_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = MainFeedDatabase.getInstance(application).database
        val profileDatabase = ProfileDatabase.getInstance(application).database
        val followDatabase = FollowDatabase.getInstance(application).database

        val viewModelFactory = MyFeedViewModelFactory(idx, database, profileDatabase, followDatabase, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyFeedViewModel::class.java)

        binding.myFeedViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val clickAdapter = MyFeedListAdapter(MyFeedClickListener {
            it?.let {
                findNavController().navigate(MyFeedDirections.actionMyFeedToMyFeedDetail(it))
            }
        })
        binding.myFeedList.adapter = clickAdapter

        val manager = GridLayoutManager(activity,3)
        binding.myFeedList.layoutManager = manager

        binding.addMyfeed.setOnClickListener {
            findNavController().navigate(MyFeedDirections.actionMyFeedToMyFeedWrite())
        }

        viewModel.selectAll.observe(viewLifecycleOwner, Observer {

            it?.let {
                clickAdapter.submitList(it)
            }
        })
        binding.moreMenuBtn.setOnClickListener {
            val intent: Intent = Intent(requireActivity(), BottomMenu::class.java)
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivityForResult(intent, CommonCode.MYPAGE_ACTIVITY_RESULT)
        }

        viewModel.myProfile.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            it?.let {
                binding.nameKid.text = it.name.toString()
                binding.numFollower.text = it.follower.toString()
                binding.numFollowing.text = it.following.toString()

                Glide.with(application).load(it.img).into(binding.shapeableImageView)

                binding.introText.text = it.introduce.toString()

            }
        })

        binding.dailycheckBtn.setOnClickListener {
            findNavController().navigate(MyFeedDirections.actionMyFeedToChattingList())
            requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }

        viewModel.followerResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.numFollower.text = it.size.toString()
            }
        })
        viewModel.followeeReusult.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.numFollowing.text = it.size.toString()
            }
        })

        viewModel.followChecker.observe(viewLifecycleOwner, Observer {
            Log.e("follwChecker","============================================================")
            Log.e("follwChecker","${it}")
            Log.e("follwChecker","============================================================")
        })

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == CommonCode.MYPAGE_ACTIVITY_RESULT) {

            data?.let {
                it.extras?.let {
                    val itemSelected = it.get("selectedItem")

                    when (itemSelected) {
                        0 -> {
                            findNavController().navigate(MyFeedDirections.actionMyFeedToMyAlarm())
                        }
                        1 -> {
                            findNavController().navigate(MyFeedDirections.actionMyFeedToMyProfile())
                        }
                        2 -> {
                            findNavController().navigate(MyFeedDirections.actionMyFeedToMyFamily())
                        }
                        3 -> {
                            findNavController().navigate(MyFeedDirections.actionMyFeedToThemeSetting())
                        }
                        4 -> {
                            val intent: Intent = Intent(requireActivity(), Resign::class.java)
                            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            startActivity(intent)
                        }
                        5 -> {
                            val intent: Intent = Intent(requireActivity(), Login::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and  Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }

                }




            }


        }


    }



}