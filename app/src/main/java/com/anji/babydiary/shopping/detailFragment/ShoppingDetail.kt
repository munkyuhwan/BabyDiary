package com.anji.babydiary.shopping.detailFragment

import android.content.Intent
import android.net.Uri
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
import com.anji.babydiary.database.shopping.ShoppingDatabase
import com.anji.babydiary.databinding.ShoppingDetailFragmentBinding
import com.anji.babydiary.mainFeed.feedDetail.FeedDetailArgs
import com.anji.babydiary.shopping.ShoppingActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main_feed.*
import kotlinx.android.synthetic.main.write_product_fragment.view.*

class ShoppingDetail : Fragment() {


    private lateinit var binding:ShoppingDetailFragmentBinding
    private lateinit var viewModel: ShoppingDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().fab.visibility = View.GONE

        val argment = arguments?.let {ShoppingDetailArgs.fromBundle(it) }
        val idx = argment!!.shoppingIdx

        binding = DataBindingUtil.inflate(inflater,R.layout.shopping_detail_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application
        val database = ShoppingDatabase.getInstance(application).database

        val viewModelFactory = ShoppingDetailViewModelFactory(database, idx, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingDetailViewModel::class.java)

        binding.viewModel = viewModel


        val nameArray = arrayOf(
            "승율아가",
            "찬호",
            "쥬쥬",
            "오쑥이",
            "선우",
            "승현아기",
            "말랑이",
            "재재"
        )

        val imgArray = arrayOf(
            "",
            "mem_1",
            "mem_2",
            "mem_3",
            "mem_4",
            "mem_5",
            "mem_6",
            "mem_7",
            "mem_8"
        )
        binding.writer.text = nameArray[ idx.toInt()%nameArray.size ]

        binding.toLink.setOnClickListener {
            //if (!viewModel.url.value.equals("")) {
                showInBrowser(viewModel.url.value!!)
            //}
        }

        viewModel.data.observe(viewLifecycleOwner, Observer {

            it?.let {

                binding.productInput.text = it.title
                binding.productPrice.text = it.price

                if (it.url.equals("")) {
                    binding.toLink.visibility = View.GONE
                }

                val url = it.url
                viewModel.url.value = url

                Glide.with(binding.root.context)
                    .load(resources.getIdentifier(imgArray[ idx.toInt()%imgArray.size ], "drawable", requireActivity().packageName))
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                    .into(binding.userIcon)

                if (it.imgDir.startsWith("second") || it.imgDir.startsWith("recomm")) {
                    Glide.with(binding.root.context)
                        .load(  resources.getIdentifier(it.imgDir, "drawable", application.packageName))
                        //.apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.imageView)
                    //Utils.setFeedListImg(binding.productImage)
                }else {
                    Glide.with(binding.root.context)
                        .load(it.imgDir)
                        //.apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(50)))
                        .into(binding.imageView)
                    //Utils.setFeedListImg(binding.productImage)
                }




            }

        })

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }


    fun showInBrowser(url:String) {
        Log.e("url","url: ${url}")
        if (!url.equals("") || url != null) {
            var intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("${url}")
            startActivity(intent)
        }
    }
}