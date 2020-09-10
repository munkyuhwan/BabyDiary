package com.anji.babydiary.common

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anji.babydiary.R
import com.anji.babydiary.common.bottomNavigation.BottomNavigationViewModel
import com.anji.babydiary.common.bottomNavigation.BottomNavigationViewModelFactory
import com.anji.babydiary.dailyCheck.DailyCheckViewModel
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.event.EventActivity
import com.anji.babydiary.gnb.main.NavViewModel
import com.anji.babydiary.gnb.main.NavViewModelFactory
import com.anji.babydiary.mainFeed.MainFeedActivity
import com.anji.babydiary.myPage.MyPage
import com.anji.babydiary.shopping.ShoppingActivity
import com.anji.babydiary.tips.TipActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.nav_layout.view.*
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.random.Random


abstract class BaseActivity() : AppCompatActivity() {

    lateinit var toolbar:Toolbar;
    lateinit var layout:CollapsingToolbarLayout;
    lateinit var navController:NavController
    lateinit var navViewModel: NavViewModel
    lateinit var selectAll:LiveData<List<Profiles>>
    var job = Job()
    var uiScope = CoroutineScope(Dispatchers.Main + job)
    lateinit var database: ProfileDao
    lateinit var feedDatabase:MainFeedDAO
    fun setNav(nestedHost:Int):NavViewModel {
        var appBarConfiguration: AppBarConfiguration.Builder
        var navViewClosedHeight:Int = 278
        val navViewModelFactory = NavViewModelFactory(application)
        navViewModel = ViewModelProviders.of(this, navViewModelFactory).get(NavViewModel::class.java)


        navController = this.findNavController(nestedHost)

        if (nestedHost == R.id.eventNestFragment) {
            layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_event_layout)
            toolbar = findViewById<Toolbar>(R.id.activity_event_toolbar)
        }else {
            layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
            toolbar = findViewById<Toolbar>(R.id.activity_toolbar)
            var navView = toolbar.nav_category
            navView.animate().translationY(navView.height.toFloat()).setDuration(1000)
        }


        //toolbar.navigationIcon!!.setVisible(false, false)
        //toolbar.setNavigationIcon(R.drawable.ic_launcher_background)
        setSupportActionBar(toolbar)


        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
        layout.isTitleEnabled=false

        layout.setupWithNavController(toolbar, navController, appBarConfiguration.build())

        navViewModel.isOpen.observe(this, Observer {
            //layout.nav_category.bringToFront()
            it?.let {
                var layoutParam = layout.layoutParams
                if (!it) {
                    layoutParam.height = 278
                }else {
                    layoutParam.height = 578
                }
            }

        })




        return navViewModel
    }


    fun insertData(profile:Profiles) {
        uiScope.launch {
            Log.e("member", "launch")
            insert(profile)
        }
    }

    private suspend fun insert(profile:Profiles) {
        withContext(Dispatchers.IO){
            Log.e("member", "insert")
            try {
                database.insert(profile)
            }catch (ex:Exception){

            }
        }
    }

    val nameArray = arrayOf(
        "",
        "승율아가",
        "찬호",
        "쥬쥬",
        "오쑥이",
        "선우",
        "승현아기",
        "말랑이",
        "재재"
    )

    val intro= arrayOf(
        "",
        "승유리를 소개해요",
        "똘망똘망한 먹보",
        "포토그래퍼의 보물",
        "우리집 사랑둥이",
        "착할선 번우",
        "이슬부부의 뮤즈",
        "작고 소중한 우리의 천사",
        "세상으로 나오는 날을 기다리는 중"
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

    fun doInsert(i:Int) {
        database = ProfileDatabase.getInstance(this).database
        selectAll = database.selectAll()
        //for (i in 1..10) {
        var profile = Profiles()
        profile.idx = i.toLong()
        profile.name = nameArray[i]
        profile.id = i
        profile.pass = "${i}"
        profile.introduce = "${intro[i]}"
        profile.imgTmp = imgArray[i]
        insertData(profile)
        //}
    }


    suspend fun insertFeed(mainFeed:MainFeed) {
        withContext(Dispatchers.IO) {
            feedDatabase.insert(mainFeed)
        }
    }

    fun insertFeed(userIdx:Long,
                   title:String,
                   toSpouser:String,
                   height:Long,
                   weight:Long,
                   head:Long,
                   imgTmpDir:String,
                   type:String,
                   year:Int,
                   month:Int,
                   date:Int
    ){
        feedDatabase = MainFeedDatabase.getInstance(this).database
        var mainFeed = MainFeed()

        mainFeed.userIdx = userIdx
        mainFeed.title = title
        mainFeed.toSpouser = toSpouser
        mainFeed.height = height
        mainFeed.weight = weight
        mainFeed.head = head
        mainFeed.imgTmpDir = imgTmpDir
        mainFeed.feedType = type

        mainFeed.year = year
        mainFeed.month = month
        mainFeed.date = date

        //mainFeed.timeMilli =

        uiScope.launch {
            insertFeed(mainFeed)
        }

        if (imgTmpDir.equals("feed_7")) {

        }

        /*
        mainFeed.title = "제목"
        mainFeed.height = Math.random().toLong()
        mainFeed.weight = Math.random().toLong()
        mainFeed.head = Math.random().toLong()
        mainFeed.location = "d"
        mainFeed.toSpouser = "ㅇㅇ 수고했다"
        mainFeed.imgDir = imgArray[Random.nextInt(0,9)]
        mainFeed.userIdx = idx

         */

    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                //or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                //or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                //or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.transparency));

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        hideSystemUI()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }



    fun setBottomNav(idx:Int):BottomNavigationViewModel {
        val bottomNavViewModelFactory = BottomNavigationViewModelFactory(this, idx)
        val bottomNavViewModel = ViewModelProviders.of(this, bottomNavViewModelFactory).get(BottomNavigationViewModel::class.java)
        return bottomNavViewModel
    }


    fun eventIntent() {
        val intent: Intent = Intent(this, EventActivity::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)
    }

    fun tipIntent() {
        val intent: Intent = Intent(this, TipActivity::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)
    }
    fun shoppingIntent() {

        val intent: Intent = Intent(this, ShoppingActivity::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)

    }

    fun mainIntent() {

        val intent: Intent = Intent(this, MainFeedActivity::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)

    }

    fun myPageIntent() {
        val intent: Intent = Intent(this, MyPage::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)

    }

    fun bindGNB() {

    }



    fun dailyCheckDrawerSetting(
        drawerLayout:DrawerLayout,
        drawerWrapper:ConstraintLayout,
        fab:FloatingActionButton,
        dailyCheckViewModel: DailyCheckViewModel
        )
    {
        //drawerlayout, dailyCheckVIewModel, drawerWrapper, fab

        /*
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


        drawerWrapper.calendarView.setOnDateChangeListener { calendarView, y, m, d ->
            val calendar = Calendar.getInstance()
            calendar[y, m] = d
            val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]

            dailyCheckViewModel.selectedYear.value = y.toString()
            dailyCheckViewModel.selectedMonth.value = m.toString()
            dailyCheckViewModel.selectedDate.value = d.toString()
            dailyCheckViewModel.onDaySelect(dayOfWeek)
        }
        fab.setOnClickListener {
            //drawerLayout.openDrawer(Gravity.LEFT)
        }

         */

    }


}