package com.anji.babydiary.myPage.themeSetting

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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

        return binding.root

    }


}