package com.anji.babydiary.myPage.myFamily

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.family.Family
import com.anji.babydiary.database.family.FamilyDao
import kotlinx.coroutines.*

class MyFamilyViewModel(val database:FamilyDao, application: Application) : AndroidViewModel(application) {

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    val selectAll = database.selectAll()

    init {

    }


    fun addFamily(familyName:CharSequence) {

        var family:Family = Family()

        family.family_name = familyName.toString()
        family.family_title = "아빠"

        uiScope.launch {
            insert(family)
        }
    }

    suspend fun insert(family:Family) {
        withContext(Dispatchers.IO) {
            database.insert(family)
        }
    }




}