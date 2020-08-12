package com.anji.babydiary.common

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.anji.babydiary.mainFeed.MainFeedActivity

class GNBClicked():ViewModel() {

    fun goHome(view: View) {
        val intent: Intent = Intent(view.context, MainFeedActivity::class.java)
        OnGNBClicked.startActivity(intent)
        OnGNBClicked.finish()
    }
}