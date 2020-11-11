package com.anji.babydiary.myPage.myFeedWrite.templateSelect

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.databinding.MyFeedWriteFragmentBinding
import com.anji.babydiary.databinding.TemplateSelectFragmentBinding
import com.anji.babydiary.databinding.TemplateSelectFragmentBindingImpl
import com.anji.babydiary.myPage.myFeedWrite.MyFeedWriteDirections

class TemplateSelect : Fragment() {

    companion object {
        fun newInstance() = TemplateSelect()
    }

    private lateinit var viewModel: TemplateSelectViewModel
    private lateinit var binding: TemplateSelectFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<TemplateSelectFragmentBinding>(
            inflater,
            R.layout.template_select_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application


        val viewModelFactory = TemplateSelectViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TemplateSelectViewModel::class.java)
        binding.viewModel = viewModel


        binding.nextButton.setOnClickListener {
            viewModel.selectedTemplate.value.let {
                findNavController().navigate(TemplateSelectDirections.actionTemplateSelectToMyFeedWrite(it!!))
            }
        }

        binding.templateOne.setOnClickListener {
            it.setBackgroundColor(Color.GRAY)
            binding.templateTwo.setBackgroundColor(Color.WHITE)
            binding.templateThree.setBackgroundColor(Color.WHITE)
            binding.templateFour.setBackgroundColor(Color.WHITE)
            viewModel.selectTemplate(1)
        }

        binding.templateTwo.setOnClickListener {
            it.setBackgroundColor(Color.GRAY)
            binding.templateOne.setBackgroundColor(Color.WHITE)
            binding.templateThree.setBackgroundColor(Color.WHITE)
            binding.templateFour.setBackgroundColor(Color.WHITE)
            viewModel.selectTemplate(2)
        }

        binding.templateThree.setOnClickListener {
            it.setBackgroundColor(Color.GRAY)
            binding.templateOne.setBackgroundColor(Color.WHITE)
            binding.templateTwo.setBackgroundColor(Color.WHITE)
            binding.templateFour.setBackgroundColor(Color.WHITE)
            viewModel.selectTemplate(3)
        }

        binding.templateFour.setOnClickListener {
            it.setBackgroundColor(Color.GRAY)
            binding.templateOne.setBackgroundColor(Color.WHITE)
            binding.templateTwo.setBackgroundColor(Color.WHITE)
            binding.templateThree.setBackgroundColor(Color.WHITE)
            viewModel.selectTemplate(4)
        }

        return binding.root
    }

}