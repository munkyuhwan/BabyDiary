package com.anji.babydiary.myPage.themeSetting

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.databinding.ThemeSettingFragmentBinding

class ThemeSetting : Fragment() {

    private lateinit var viewModel: ThemeSettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<ThemeSettingFragmentBinding>(inflater, R.layout.theme_setting_fragment, container, false)

        val viewModelFactory = ThemeSettingViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ThemeSettingViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.themeSettingViewModel = viewModel

        viewModel.theme.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.mainLayout.setBackgroundColor(Color.parseColor(it))

            }
        })

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root

    }


}