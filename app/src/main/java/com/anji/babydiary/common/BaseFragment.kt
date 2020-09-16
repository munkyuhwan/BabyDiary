package com.anji.babydiary.common

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseFragment : Fragment() {

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    fun checkProfile(application: Application) {

        val profileChecker = ProfileDBChecker(application)

        profileChecker.profileResult.observe(viewLifecycleOwner, Observer {
            Log.e("profileresult","=============================================================")
            Log.e("profileresult","${it}")
            Log.e("profileresult","=============================================================")

            if (it == null) {
               // profileChecker.addProfile()
            }

        })

        profileChecker.checkProfile(requireContext())

    }

}