package com.anji.babydiary.tips

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.AppBarConfiguration
import com.anji.babydiary.R
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.databinding.ActivityTipBinding

class TipActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration.Builder
    private lateinit var viewModel: TipActivityViewModel
    private lateinit var binding: ActivityTipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_tip)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_tip)
        viewModel = TipActivityViewModel(application)
        binding.tipViewModel = viewModel

        setNav(R.id.tipNestedHost)

    }
}