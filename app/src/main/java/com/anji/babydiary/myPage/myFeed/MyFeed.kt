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
import com.anji.babydiary.addBaby.AddBaby
import com.anji.babydiary.bottomActivity.BottomMenu
import com.anji.babydiary.bottomActivity.resign.Resign
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.follow.Follow
import com.anji.babydiary.database.follow.FollowDatabase
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.databinding.MyFeedFragmentBinding
import com.anji.babydiary.login.Login
import com.anji.babydiary.mainFeed.feedDetail.FeedDetailArgs
import com.anji.babydiary.myPage.myFeed.dateListAdpater.DateListAdapter
import com.anji.babydiary.myPage.myFeed.myFeedListAdapter.MyFeedListAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.nav_mypage_layout.view.*

class MyFeed : Fragment() {


    private lateinit var viewModel: MyFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().fab.visibility = View.VISIBLE

        var idx = MyShare.prefs.getLong("idx", 0L)

        val intent = requireActivity().intent.extras
        intent?.let {
            idx = it.getLong("userIdx")
        }

        val binding = DataBindingUtil.inflate<MyFeedFragmentBinding>(inflater, R.layout.my_feed_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = MainFeedDatabase.getInstance(application).database
        val profileDatabase = ProfileDatabase.getInstance(application).database
        val followDatabase = FollowDatabase.getInstance(application).database


        val viewModelFactory = MyFeedViewModelFactory(idx, database, profileDatabase, followDatabase, MyShare.prefs.getLong("idx", 0L), application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyFeedViewModel::class.java)

        binding.myFeedViewModel = viewModel
        binding.setLifecycleOwner(this)


        binding.addMyfeed.setOnClickListener {
            findNavController().navigate(MyFeedDirections.actionMyFeedToMyFeedWrite())
        }

        viewModel.selectAll.observe(viewLifecycleOwner, Observer {

            it?.let {
               // clickAdapter.submitList(it)
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
                //binding.numFollower.text = it.follower.toString()

                //binding.numFollowing.text = it.following.toString()

                if (!it.img.equals("")) {
                    Glide.with(application).load(it.img).into(binding.shapeableImageView)
                }
                if (!it.imgTmp.equals("")) {
                    Glide.with(application).load(  resources.getIdentifier(it.imgTmp, "drawable", requireActivity().packageName)).into(binding.shapeableImageView)
                }

                binding.introText.text = it.introduce.toString()

            }
        })

        binding.dailycheckBtn.setOnClickListener {
            findNavController().navigate(MyFeedDirections.actionMyFeedToChattingList())
            requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }

        viewModel.followerResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.e("followers","${it}")
                binding.numFollower.text = it.size.toString()
            }
        })
        viewModel.followeeReusult.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.e("followee","================================================================================")
                Log.e("followee","${it}")
                Log.e("followee","================================================================================")
                binding.numFollowing.text = it.size.toString()
            }
        })


        val dateAdapter = DateListAdapter(MyFeedClickListener {
            it?.let {
                findNavController().navigate(MyFeedDirections.actionMyFeedToMyFeedDetail(it))
            }
        }, requireActivity(), database, findNavController(), viewLifecycleOwner)
        binding.monthList.adapter = dateAdapter


        viewModel.selectDates.observe(viewLifecycleOwner, Observer {
            dateAdapter.submitList(it)
        })


        binding.followerLabel.setOnClickListener {
            if (idx == MyShare.prefs.getLong("idx", 0L)) {
                findNavController().navigate(MyFeedDirections.actionMyFeedToFollower("follower"))
            }
        }
        binding.numFollower.setOnClickListener {
            if (idx == MyShare.prefs.getLong("idx", 0L)) {
                findNavController().navigate(MyFeedDirections.actionMyFeedToFollower("follower"))
            }
        }

        binding.followingLabel.setOnClickListener {
            if (idx == MyShare.prefs.getLong("idx", 0L)) {
                findNavController().navigate(MyFeedDirections.actionMyFeedToFollower("following"))
            }
        }
        binding.numFollowing.setOnClickListener {
            if (idx == MyShare.prefs.getLong("idx", 0L)) {
                findNavController().navigate(MyFeedDirections.actionMyFeedToFollower("following"))
            }
        }


        binding.nameKid.setOnClickListener {
            val intent: Intent = Intent(requireActivity(), AddBaby::class.java)
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity(intent)
        }
        binding.moreCategory.setOnClickListener {
            val intent: Intent = Intent(requireActivity(), AddBaby::class.java)
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity(intent)
        }


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

    override fun onResume() {
        super.onResume()
        viewModel.loadDates()
    }



}