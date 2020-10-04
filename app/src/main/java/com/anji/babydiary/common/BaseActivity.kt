package com.anji.babydiary.common

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import com.anji.babydiary.R
import com.anji.babydiary.common.bottomNavigation.BottomNavigationViewModel
import com.anji.babydiary.common.bottomNavigation.BottomNavigationViewModelFactory
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.tip.Tips
import com.anji.babydiary.database.tip.TipsDao
import com.anji.babydiary.database.tip.TipsDatabase
import com.anji.babydiary.event.EventActivity
import com.anji.babydiary.gnb.main.NavViewModel
import com.anji.babydiary.mainFeed.MainFeedActivity
import com.anji.babydiary.myPage.MyPage
import com.anji.babydiary.shopping.ShoppingActivity
import com.anji.babydiary.tips.TipActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.*


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
    lateinit var tipDatabase:TipsDao
    var userData:LiveData<List<Profiles>>? = null
    var feedData:LiveData<List<MainFeed>>? = null


    init {
        Log.e("user","================init========================================")

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
        selectAll = database.selectAll()
        //for (i in 1..10) {
        var profile = Profiles()
        profile.idx = i.toLong()
        profile.name = nameArray[i]
        profile.id = i
        profile.pass = "${i}"
        profile.introduce = "${intro[i]}"
        profile.imgTmp = imgArray[i]
        uiScope.launch {
            insert(profile)
        }
        //}
    }

    private suspend fun insert(profile:Profiles) {
        withContext(Dispatchers.IO){
            Log.e("dataInsert", "member insert")
            database.insert(profile)
        }
    }

    suspend fun insertFeed(mainFeed:MainFeed) {
        withContext(Dispatchers.IO) {
            Log.e("dataInsert", "feed insert")
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
                   date:Int,
                   hour:Int,
                   min:Int
    ){
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

        mainFeed.timeMilli = Utils.getMilliFromDate("${year} ${month} ${date} ${hour}:${min}")

        uiScope.launch {
            insertFeed(mainFeed)
        }

    }

    fun initUserData () {
        database = ProfileDatabase.getInstance(this).database
        feedDatabase = MainFeedDatabase.getInstance(this).database
        tipDatabase = TipsDatabase.getInstance(this).database
        userData = database.checkUserData()
        feedData = feedDatabase.selectAll()
    }

    fun initializeData() {
        userDataInitialize()
        tipInitialize()
    }


    fun userDataInitialize() {
            doInsert(1)
            doInsert(2)
            doInsert(3)
            doInsert(4)
            doInsert(5)
            doInsert(6)
            doInsert(7)
            doInsert(8)
    }

    fun tipInitialize() {
        insertTip(0)
        insertTip(1)
        insertTip(2)
        insertTip(3)
        insertTip(4)
        insertTip(5)
        insertTip(6)
        insertTip(7)
        insertTip(8)
        insertTip(9)
        insertTip(10)
        insertTip(11)
        insertTip(12)
        insertTip(13)
        insertTip(14)
        insertTip(15)
        insertTip(16)
        insertTip(17)
    }

    fun insertTip(i:Int) {
        var tip = Tips()
        tip.user_idx = CommonCode.TIP_DATA_USER[i]
        tip.category = "${CommonCode.TIP_DATA_CAT[i]}"
        tip.text = "${CommonCode.TIP_DATA_TXT[i]}"
        tip.imgDirTmp = "${CommonCode.TIP_DATA_IMG[i]}"
        uiScope.launch {
            doInsertTip(tip)
        }

    }

    private suspend fun doInsertTip(tip: Tips) {
        withContext(Dispatchers.IO) {
            tipDatabase.insert(tip)
        }
    }

    fun feedDataCheck() {
        uiScope.launch {
            selectFeedData()

            //feedDataInitialize()
        }
    }
    suspend fun selectFeedData() {
        withContext(Dispatchers.IO) {
            feedData = feedDatabase.selectAll()
        }
    }

    fun feedDataInitialize() {
        insertFeed(1,
            "생후 40일 우리아가♥\n" +
                    "맘마 먹고 낮잠 자야하는데 말똥말똥 엄마만 바라보는 우리 승율이 ㅎㅎ\n" +
                    "꼬물꼬물 어찌 이런 작은 몸을 움직이는지 신기할 따름>< ㅎㅎ\n" +
                    "아직도 엄마가 된 게 실감이 안나지만 우리 같이 으쌰으쌰 잘 해보자!",
            "여보! 여보라는 애칭이 이제야 익숙해지네요ㅎㅎ 아빠사진을 보여주니 승율이가 방긋방긋해요! 열일하고 집에서 봐요~ ",
            67,
            7,
            40,
            "feed_1",
            "area",
            2019,
            9,
            1,
            10,10
        )
        insertFeed(1,
            "ㅡㅡ^ ..? 우리 아들 응가하는 중! 눈썹이 아직 옅어서 모나리자 같지만 너무 귀엽다 너♥\n" +
                    "힘주니 콧구멍이 동그래지는구나 어쩜이리 사랑스러울까ㅎㅎ\n" +
                    "너의 눈, 코, 입, 손, 발 하나하나가 신기하고 감사한 요즘이야\n" +
                    "엄마 아빠가 더 노력할테니 지금처럼 건강하게 쑥쑥크자 우리아가♥",
            "오늘 우리 승율이 황금응가를 했어요! 당신이 퇴근하며 사다주는 건강한 음식들 덕에 모유도 건강한가봐ㅎㅎ 늘 고마워요♥",
            70,
            7,
            42,
            "feed_2",
            "area",
            2019,
            11,
            15,
            13,10
        )
        insertFeed(1,
            "오잉? 잠들기 전 자장가 불러주니 재롱부리는 끼쟁이 ><♥\n" +
                    "애교가 많은 걸 보니 아빠를 닮았구나! ㅋㅋㅋ \n" +
                    "엄마가 아직 서툴러서 허둥지둥 대다가 지칠 때도 있지만 이런 너의 행동 하나하나에 다시 힘이 나♥\n" +
                    "천사같은 우리아들 오늘도 엄마아빠 곁에 와준것에 감사해 ♥\n" +
                    "내일은 아빠가 더 잘해줄거야ㅎㅎ ",
            "승율이가 여보를 닮아 애교가 듬뿍이에요ㅎㅎ \n" +
                    "퇴근하고 늘 승율이와 함께해주고 내 생각해줘서 너무 고마워요! \n" +
                    "내일이 승율이 태어나고 처음 단둘이 함께하는 하루가 된다고 설레여하는 당신을 보니 뿌듯하기도 하고 걱정도 되네요....ㅎㅎ \n" +
                    "하지만 당신과 승율이는 판박이라서 잘 통할거야 그치? 파이팅 건투를 빌어요! 사랑해요♥♥♥",
            70,
            7,
            42,
            "feed_3",
            "area",
            2019,
            1,
            5,
            15,0
        )

        insertFeed(1,
            "승율아 엄마가 늘 너는 아빠의 판박이라고 했는데 정말이였구나? 아빠를 닮았으니 늠름하고 멋있는 싸나이가 될 것이 분명해!! 오늘 처음 엄마 없이 아빠랑만 있었는데도 낯설어하지 않고 이렇게 쌔근쌔근 예쁘게 낮잠도 자줘서 아빠는 너무 고맙단다! 엄마랑 있을 때 보다 더 편안히 자는 것 같은 생각은 기분탓인거니..? ㅎㅎ 우리 조금 더 지나면 더 멋있는 남자가 되어서 엄마를 지켜주자! 우리아들 파이팅- ",
            "여보 나 오늘 좀 잘한 것 같아! 처음엔 승율이가 엄마가 없으니 살짝 어색해 하는 것 같았지만 한번도 울지 않고 나랑 재밌는 하루 보냈어!ㅎㅎ 요 며칠 매일 일하느라 밤늦은 시간밖에 당신을 보지 못해서 너무 슬프고 미안했어..ㅠㅠ 앞으로는 당신 스트레스 풀 시간 더 많이 갖을 수 있도록 노력할게! 늘 고마워요 내사랑♥",
            80,
            8,
            50,
            "feed_4",
            "age",
            2020,
            3,
            1,
            14,30
        )
        insertFeed(1,
            "잠을 잘자서 우리아가가 이렇게 쑥쑥 크고있는 건가? \n" +
                    "하루하루 지날수록 살이 붙어 어느새 포동포동해졌네ㅎㅎ \n" +
                    "어제보다 오늘 더 성장하는 승율이만큼 엄마랑 아빠도 정말 부모가 되었다는 것을 실감해!\n" +
                    "승율이가 있어 엄마아빠가 얼마나 행복한지 몰라! \n" +
                    "승율이도 엄마아빠와 함께하는 시간이 늘 즐겁길바래♥ \n",
            "",
            80,
            8,
            50,
            "feed_5",
            "age",
            2020,
            5,
            30,
            17,10
        )

        insertFeed(1,
            "귀여운 우리아들! 요즘은 왜이렇게 잠자는걸 싫어하는 거니~ \n" +
                    "엄마아빠를 조금이라도 더 보고싶은거야?ㅎㅎㅎ 내일은 외할머니가 돌봐주실거야~ 간만에 엄마아빠는 바깥나들이를 해보려고해 ㅎㅎ\n" +
                    "우리 아가는 아직 코로나19에 맞서기엔 조금 더 튼튼해질 필요가 있어! \n" +
                    "더 쑥쑥 커서 엄마아빠랑 소풍가자 우리 아들-♥",
            "여보! 내일은 우리 간만에 단둘이 데이트 하는 날이에요 ㅎㅎ 어제부터 들떠하던 당신모습이 승율이 같이 너무 귀여워! 여자인 나보다 애교 많고 자상한 내남편! 내일 우리 맛있는 음식도 많이 먹고 자동차극장가서 영화도 보자! 옆에서 쿨쿨 자고 있는 모습마저 귀엽네요! 굿나잇♥",
            83,
            9,
            53,
            "feed_6",
            "age",
            2020,
            8,
            28,
            9,43
        )

        insertFeed(1,
            "우리 모찌모찌 승율이! 오늘은 아빠랑 엄마랑 나들이 하고 온 날! 오랜만에 바깥바람 쐰다고 예쁘게 꾸미니 너의 큐티함이 한껏 업그레이드 되는구나! ㅋㅋㅋ 엄마를 닮은거니?\n" +
                    "이제는 옹알이를 하는건지 오물오물하는 우리 아들,\n" +
                    "오늘도 승율이 덕에 아빠엄마는 행복했어 내일도 아빠랑 신나게 놀아보자 아들!\n",
            "귀여운 여보~ 오늘 두 남자 데리고 산책하느라고 고생했어요 \n" +
                    "내눈엔 당신도 아가같은데 승율이를 안고 있는 모습을 보면 미안하기도 하고 고맙기도 해요! 이런게 행복이겠지? 내가 더 잘할게 사랑해 내 아내♥",
            86,
            10,
            55,
            "feed_7",
            "age",
            2020,
            12,
            3,
            10,20
        )

        /////idx:2
        insertFeed(2,
            "찬호가 우리 곁으로 온지, 세식구가 된지 어느새 한달.\n" +
                    "아직은 너무 서툰 부모라 미안 할 따름..\n" +
                    "아빠도 아빠가 처음이라 찬호가 원하는 걸 잘 모를 때가 많아..ㅠ\n" +
                    "아빠도 열심히 해볼테니 찬호도 노력해줘 ^^ 잘해보자 우리!",
            "세영아 요즘 수유하랴 새벽에 찬호 신경쓰랴 정말 고생했어. 조만간 찬호 친가에 잠시 맡겨두고 힐링하러 가자! 내일 월차니까 오늘 새벽은 내가 책임질게! 푹자♥",
            86,
            10,
            55,
            "feed_2_1",
            "age",
            2019,
            4,
            5,
            11,20
        )

        insertFeed(2,
            "까꿍♥ 오늘은 왠일로 눈뜨자마자 기분 좋은 찬둥이ㅎㅎ 영주이모가 선물해준 아기배게 덕분인가? 덕분에 엄마도 오늘 굿모닝이야!\n" +
                    "그나저나 오늘 아빠 출장가서 엄마랑 우리 찬이랑 단둘이 자겠네! 그래도 요즘은 밤에 잘 깨지 않고 잘 자줘서 엄마가 너무 고마워ㅎㅎ 오늘 밤도 제발 굿밤이길!",
            "출장가서도 늘 내걱정, 찬이 걱정해줘서 고마워! 오랜만에 떨어져있으니 당신의 빈자리가 크다ㅠㅠ 언능와 꼬옥 안아줄게!",
            86,
            10,
            55,
            "feed_2_2",
            "age",
            2019,
            9,
            15,
            11,20
        )

        insertFeed(2,
            "뭐시맘에 안드는거야~ 요즘들어 투정도 많아지고맨날 엄마아빠 먹는 것만 따라 먹으려고 하는 찬호ㅠㅠ\n" +
                    "찬호야 이건 아직 너가 못먹는 음식들이란다!ㅠ 찬호가 이다음에 커서 어린이 되면 엄마가 제일 먼저 맛있는거 많이 사줄게ㅎㅎ 오늘은 이유식먹쟈!",
            "여보오 나는 오늘 매콤한 떡볶이가 먹고싶네..? 퇴근하고 오는길에 두손 무겁게 들어오는 당신을 기다리고 있을게~ 사랑해! 알러뷰♥",
            86,
            10,
            55,
            "feed_2_3",
            "age",
            2019,
            9,
            15,
            11,20
        )

        insertFeed(2,
            "귀저기차고 뒤뚱뒤뚱 아슬아슬하게 걸어가더니 한강 구경하는 찬뚱ㅎㅎㅎ 모든게 신기해?ㅎㅎ\n" +
                    "엄마아빠도 하루가 다르게 커가는 찬뚱이를 보니 너무너무 신기해~ 요즘은 엄마아빠 부르기도하고 울애기 천재 아니야? 영재학교 가야하는거니? 아니야 아니야 공부는 못해도 돼! 건강하게만 자라줘 우리 아들~♥",
            "오랜만에 우리 세식구 나들이가서 너무 좋았어~ 내가 요즘 많이 예민해서 짜증부려서 미안해..ㅎㅎ 직접 말로하기는 조금 민망해서 여기에라두 사과하고 난 이만 총총! ㅎㅎ 아맞다 사랑해♥",
            86,
            10,
            55,
            "feed_2_4",
            "age",
            2020,
            4,
            8,
            11,20
        )

        insertFeed(2,
            "요근래 촉감놀이에 빠져있는 찬호. 그렇다고 모래찰흙 만지고 입으로 가져가면 어떡하니 엄마오면 아빠 엄청 혼나겠다!ㅠ 아빠를 닮아 장이 건강해서 다행이야 엄마 닮았으면 찬호는 오분마다 화장실행이였을거야...ㅎ 그래도 앞으로 촉감놀이는 되도록 음식으로 하자 아들 ^^",
            "여보 찬호랑 촉감놀이 하고나면 늘 집청소하느라 진빠져있던 당신의 얼굴이 머릿속에서 떠나지 않던 오늘이야... 오늘따라 당신을 보고있어도 보고싶은건 기분탓일까...?",
            86,
            10,
            55,
            "feed_2_5",
            "age",
            2020,
            9,
            18,
            11,20
        )


        /////idx:3
        insertFeed(3,
            "100일 / \n" +
                    "우리딸 100일 돌사진!ㅎㅎ 엄마아빠가 찍었지만 역시 잘찍었구 우리딸은 역시 귀엽구낭♥\n" +
                    "우리 쥬쥬 나중에 모델해야하는거 아니니? 어쩜이리 예쁜포즈를 척척 잘 취할까!ㅋㅋ 다음 200일사진은 더 예쁘게 찍어줄게♥\n",
            "남편~ 오늘 100일 촬영한다고 아침부터 일어나서 김밥, 샌드위치 도시락까지 싸고 우리딸 촬영할 때 웃겨주려고 재롱부릴 소품까지 준비하느라고 정말 고생했어! 내 남편은 어쩜이리 세심할까ㅎㅎ 오늘도 당신에게 감동받은 하루야! 덕분에 쥬쥬낳고 여전히 사랑받는 여자로 살아가고 있어요♥",
            86,
            10,
            55,
            "feed_3_1",
            "age",
            2020,
            6,
            22,
            11,20
        )

        insertFeed(3,
            "200일 / \n" +
                    "쥬쥬 200일 기념 촬영-\n" +
                    "100일사진 찍은게 엊그제 같은데 벌써 200일이라니! \n" +
                    "100일사진과 비교해보니 정말 우리 딸 커가고 있구나 새삼 느끼는 중♥\n" +
                    "오늘 컨셉은 바다야ㅎㅎ 요즘 코로나 때문에 진짜 바다를 보여줄 순 없지만 엄마아빠가 널 위해 이불로 바다를 준비해봤는데 어때 맘에 드니? 표정을 보니 맘에 드는거 같구낭!ㅋㅋ 우리 딸 200일 축하해! 200일동안 엄마아빠 옆에 있어줘서 고마워♥ 사랑해♥",
            "벌써 우리가 쥬쥬랑 함께한지 200일이야~ 바다 못간다고 이불로 바다를 준비하자는 제안 아주 좋았어^^ 아이디어 뱅크 내사랑ㅎㅎ 오늘 쥬쥬도 사진 찍는다고 피곤했는지 벌써 푹 잠들었으니까 오늘 우리 오랜만에 꼬옥 껴안고 자자♥",
            86,
            10,
            55,
            "feed_3_2",
            "area",
            2020,
            9,
            30,
            11,20
        )

        /////idx:4
        insertFeed(4,
            "세상에 나온지 35일된 오쑥이!!\n" +
                    "그누구보다 예쁘고 사랑스러운 오쑥아.\n" +
                    "해맑고 건강하게만 성장하길 바래!!",
            "임신후부터 나보다 더 긴장을 많이 하고 걱정을 하던 나의남편♥\n" +
                    "                  항상 자신보다 나를 먼저 생각해주고 배려해줘서 고마워요.\n" +
                    "                 우리 오쑥이랑 같이 행복하고 즐겁게 살아가자 사랑해 여보♥",
            86,
            10,
            55,
            "feed_4_1",
            "age",
            2019,
            11,
            8,
            11,20
        )
        insertFeed(4,
            "세아버지께서 긴장하며 오쑥이를 앉고 찍은 첫사진^^\n" +
                    "무럭무럭 성장해가는 모습을 보니 눈물이 왈칵ㅜ.ㅜ\n" +
                    "볼 빰빰이가 되어 웃는 오쑥이를 보면 천사를 보는듯한 느낌을 받는구나.\n" +
                    "항상 이렇게 웃으며 아빠에게 행복을 주렴..",
            "오쑥이를 출산후 우울증와서 많이 힘들었을텐데 잘 이겨내줘 고마워. \n" +
                    "하나하나 이겨내가며 서로 존중하고 더 행복하게 잘 살수있도록\n" +
                    "                 작은거 하나까지 신경쓰고 노력할게. 행복한 가정을 같이 잘 꾸며나가자♥",
            86,
            10,
            55,
            "feed_4_2",
            "area",
            2019,
            11,
            8,
            11,20
        )
        insertFeed(4,
            "오쑥이가 태어난지 365일!!!\n" +
                    "걷지는 못했지만 뒤집기는 그누구보다 잘하는 오쑥이ㅋㅋ\n" +
                    "오늘은 오쑥이가 기분이가 좋은지 많이 웃는당!! \n" +
                    "오쑥이 덕분에 집안에 웃음꽃이 피고 마음이 편해지는 기분을 받았어♥\n" +
                    "오쑥아 태어나줘서 고마워 ♥ 엄마가 처음이라 서툴겠지만 더 노력해서 오쑥이 잘 클수있게 \n" +
                    "노력할게! 많이많이 사랑해♥",
            "하나뿐인 나의 남편!! 오늘 인센티브 나왔다고 깜짝 이벤트도 해주고 이뻐죽겠어♥0♥ 항상 배려해줘서 고마워!! 내가 더더더더더 노력해서 울남편 행복한 남자로 만들어줄게요!♥♥ 사랑해♥♥",
            86,
            10,
            55,
            "feed_4_3",
            "age",
            2020,
            10,
            5,
            11,20
        )


        //idx:5

        insertFeed(5,
            "선우발가락이 나의 손가락 한마디ㅎㅎ \n" +
                    "어쩜 이렇게 귀여울수가 있을까.. 얼른 무럭무럭 자라나서 아빠랑 엄마랑 뛰어놀자꾸나.\n" +
                    "지금은 자그마한 발이지만 점차점차 커져서 세상에 대한 첫걸음을 하겠지?ㅎㅎ\n" +
                    "얼른 보고싶다 선우야!!",
            "사랑하는 선우엄마. 천사가 천사를 낳은것일까? \n" +
                    "참 신기한거 같아요 조금 더 책임감을 갖고 생각할게요\n" +
                    "항상 고맙고 많이많이 사랑해♥ ",
            86,
            10,
            55,
            "feed_5_1",
            "age",
            2019,
            1,
            15,
            11,20
        )
        insertFeed(5,
            "선우야!! 그렇게 아이컨택하면 엄마는 기절해버릴지도 몰랑ㅋㅋㅋ\n" +
                    "어쩜 이렇게 이쁠까 내아가♥ 오늘은 모유도 잘먹어줘서 고마워 선우야♥\n" +
                    "선우 덕분에 엄빠가 행복해용ㅋㅋ 울선우도 행복하겠지?? \n" +
                    "엄빠가 우리 선우 잘키울게!! 믿어줘♥",
            "오늘 나 힘들다고 마사지도 해주고 전복도 사다줘서 고마워♥ 일 끝나고 쉬고싶었을텐데 웃으며 집안일에다가 나까지 챙겨주고 고마워요ㅜ.ㅜ 나는 당신을 만나서 결혼한걸 감사하게 생각해!! 다음에는 내가 마사지해주고 맛있는거 꼭 해줄게요 사랑행♥ ",
            86,
            10,
            55,
            "feed_5_2",
            "area",
            2019,
            3,
            20,
            11,20
        )
        insertFeed(5,
            "선우 너는 불명히 하늘에서 내려온 천사일거야.!!!\n" +
                    "어떻게 세상에서 가장 예쁘게 웃을수가 있는거야!! \n" +
                    "선우가 나의 배에서 태어난 것이 신기할정도야♥ \n" +
                    "선우가 항상 웃을수있도록 엄빠는 더 노력할고 공부할거란다♥\n" +
                    "그마음은 변하지않고 행동으로 보여줄게 엄빠만 믿어♥♥나의사랑 선우♥",
            "사랑하는 남푠님!! 선우가 당신 얼굴이 나와요ㅎㅎ 참 신기하면서도 놀라워.ㅋㅋㅋ날 항상 웃게 해주는 당신의 노력에 나는 매일매일 감동을 하고 감사하게 생각해♥  얼마 전에 선우 혼자서 5시간 봐주고 잠시 쉬라며 배려해줬던 당신♥ 당신 덕분에 나는 에너지를 찾았어! 항상 배려해줘서 고마워요..ㅜ ",
            86,
            10,
            55,
            "feed_5_2",
            "area",
            2020,
            5,
            20,
            11,20
        )


        //idx:6
        insertFeed(6,
            "안녕 승현아ㅎㅎ 우리 승현이가 태어난지도 벌써 2주가 넘어가고 있어!\n" +
                    "2주 전 엄마뱃속에 있던 우리 아가가 이제는 엄마품에 있네ㅎㅎ \n" +
                    "하루하루 지날수록 승현이의 눈,코는 아빠를 닮아가고 입과 눈썹은 엄마를 닮아가는 것 같아!\n" +
                    "우리아가 이제 엄빠가 사진 많이 찍어줄게~ 넌 엄빠의 뮤즈야!♥",
            "여보~ 내가 아까 화내서 미안해..ㅎㅎ 승현이가 우니까 나도 당황했나봐요\n" +
                    "그런데도 이해해주고 잘 참아줘서 너무 고마워! 우리 서로 더 이해하고 사랑하는 멋진 부부가 되자! 사랑해요♥",
            86,
            10,
            55,
            "feed_6_1",
            "age",
            2019,
            7,
            18,
            11,20
        )
        insertFeed(6,
            "우리아가 세상에 나온지 두달하고 이틀! \n" +
                    "기념으로 찍은 사진ㅎㅎ \n" +
                    "엄마 생각에 우리 승현이는 모델이 될 인재인게 틀림 없어! 어쩜이리 귀여운 포즈를 척척 잘 취하는거니? ㅋㅋㅋ 나중에 승현이가 커서 이 사진들을 보면 참 좋아할거라 믿어! \n" +
                    "그때까지 좋은 추억 많이 만들고 새길수 있도록 엄빠가 노력할게~",
            "여보! 오늘 촬영하느라고 고생했어요ㅎㅎ 너무 웃기고 재밌고 즐거운 추억이였어! 우리 계획했던대로 틈틈이 이런 사진들 남겨서 나중에 우리의 검은머리가 파뿌리가 될 때 다시 꺼내보며 추억해요♥ 그때까지 여보를 사랑할게!♥♥♥ ",
            86,
            10,
            55,
            "feed_6_2",
            "area",
            2019,
            9,
            6,
            11,20
        )
        insertFeed(6,
            "오늘의 베스트컷-\n" +
                    "승현이 너 정말 행복해보인다ㅎㅎ 엄빠의 감정이 너에게도 전달된걸까?\n" +
                    "우리 승현이 맘마도 잘 먹는다고 엄마한테 전해들었어! 무럭무럭 쑥쑥커서 어린이가 되면 아빠랑 사우나가자 아빠의 로망이야! 같이 가주면 아빠가 우리 승현이가 좋아할만한 바나나우유를 사줄게^^ 아빠 로망 실현시켜줄거지?",
            "자기야♥ 오늘도 육아하느라고 고생많았어요ㅎㅎ 지금 이 타이밍에 이런말 해도 되는지 모르겠지만 둘째는 언제가질까 우리? ㅎㅎㅎ 나, 자기 닮은 딸 하나 있으면 소원이 없겠어요♥",
            86,
            10,
            55,
            "feed_6_3",
            "area",
            2019,
            11,
            25,
            11,20
        )
        insertFeed(6,
            "선우 너는 불명히 하늘에서 내려온 천사일거야.!!!\n" +
                    "어떻게 세상에서 가장 예쁘게 웃을수가 있는거야!! \n" +
                    "선우가 나의 배에서 태어난 것이 신기할정도야♥ \n" +
                    "선우가 항상 웃을수있도록 엄빠는 더 노력할고 공부할거란다♥\n" +
                    "그마음은 변하지않고 행동으로 보여줄게 엄빠만 믿어♥♥나의사랑 선우♥",
            "사랑하는 남푠님!! 선우가 당신 얼굴이 나와요ㅎㅎ 참 신기하면서도 놀라워.ㅋㅋㅋ날 항상 웃게 해주는 당신의 노력에 나는 매일매일 감동을 하고 감사하게 생각해♥  얼마 전에 선우 혼자서 5시간 봐주고 잠시 쉬라며 배려해줬던 당신♥ 당신 덕분에 나는 에너지를 찾았어! 항상 배려해줘서 고마워요..ㅜ ",
            86,
            10,
            55,
            "feed_6_4",
            "area",
            2020,
            5,
            20,
            11,20
        )
        insertFeed(6,
            "여러 소품중에 안경을 고른 승혀니,\n" +
                    "과학자가 될거야? 선생님이 될거야?ㅎㅎ \n" +
                    "아빠는 우리 승현이가 어떤사람이 되어도 다 좋아! 꼭 좋은 사람, 멋진 사람이 될 필요는 없단다 우리 승현이가 되고싶은 사람이 되렴! 아빠랑 엄마가 늘 응원하고 도와줄게 -\n" +
                    "하지만 아빠의 작은 바램이 있다면 밤에 푹 잤으면 좋겠어ㅎㅎㅎㅎ \n" +
                    "승현이가 자꾸 깨서 울면 엄마가 아빠 두고 승현이한테 가버린단 말이야 ㅠㅠ",
            "오늘도 너무너무 예쁜 당신! 승현이 돌보느라 고생했어요 피곤하겠지만 오늘은 나도 승현이처럼 꼭 안고 자요!!!!!ㅠㅠ ",
            86,
            10,
            55,
            "feed_6_5",
            "age",
            2020,
            2,
            16,
            11,20
        )
        insertFeed(6,
            "오늘은 우리 승현이 머리자른 기념 찰칵-\n" +
                    "너희 아빠는 지금 자기 머리 맘에 안든다고 울상인데 우리 승현이는 마음에 들었나보네ㅎㅎ\n" +
                    "다음엔 우리 승현이가 좋아하는 공룡머리 하자! 승현인 뭘해도 멋있을거야~\n" +
                    "낯설고 무서웠을텐데 울지않고 예쁘게 머리 해줘서 고마워♥ 역시 의젓한 우리 장남♥",
            "오늘 내가 다니는 미용실가서 했다구 입 삐쭉나왔네ㅎㅎ 그래도 승현이 델꼬가서 예쁘게 하고 와줘서 고마워요! 당신은 뭘해도 귀여워 오늘 하고 온 승현이랑 커플머리도 너무 귀여워 밤도토리같아♥ 다음엔 자기가 하고싶은 머리 하러가자 우리 ㅎㅎ ",
            86,
            10,
            55,
            "feed_6_6",
            "age",
            2020,
            10,
            3,
            11,20
        )

        //idx:7
        insertFeed(7,
            "곧 출산 임박 나중에 우리 말랑이 나중에 크면 보여주고 싶어서 만삭인 아내랑 사진을 찍었다\n" +
                    "우리 말랑이가 건강하게만 태어나주면 더 바랄게 없을꺼 같다\n" +
                    "바쁜 하루가 중에도 사랑하는 아내와 배 속에서 아직 태어나지 못한 아이를 생각하면 힘이 난다. 이제 진짜 몇 일 후면 나도 누군가의 아버지가 된다는 것이 실감이 나지 않는다\n",
            "만삭인 몸인데도 아침마다 뭐라도 먹고 가라며 챙겨주는 너의 모습을 보며 미안하고 고마워 우리 앞으로도 말랑이와 함께 행복한 가족이 되자 ",
            86,
            10,
            55,
            "feed_7_1",
            "age",
            2019,
            10,
            18,
            11,20
        )
        insertFeed(7,
            "\n" +
                    "말랑아 네가 내 손을 꽉 쥐었을 때 아빠는 이 순간을 놓치기 싫어서 사진을 찍었어 연애할 때 그렇게 사진 찍는 것을 싫어해서 네 엄마한테 잔소리좀 들었는데 너의 아빠가 되고나서 순간순간들을 놓치기가 싫어서 사진을 찍게 되는구나 \n" +
                    "아직 말도 못하고 울기만 하는 네가 사랑스럽구나",
            "이제 우리 사진 많이 찍고 추억 많이 남겨요ㅎㅎ ",
            86,
            10,
            55,
            "feed_7_2",
            "age",
            2020,
            1,
            2,
            11,20
        )
        insertFeed(7,
            "스튜디오에서 울지 않고 제법 의젓하게 사진 찍는 너의 모습을 보며 나를 닮았다고 하자 네 엄마가 자기 닮은거 라고 하며 웃었어 \n" +
                    "말랑이 네 덕분에 우리 가족이 웃는다\n" +
                    "말랑이라는 네 태명 때문인지 말랑말랑한 우리 말랑이 너무 고마워\n" +
                    "사진기사님도 이렇게 의젓한 아기는 처음이라며 칭찬 해주셨어",
            "의젓하게 사진 찍는 건 누가 뭐라고 해도 나를 닮은거 같아...ㅋ",
            86,
            10,
            55,
            "feed_7_3",
            "age",
            2020,
            4,
            2,
            11,20
        )
        insertFeed(7,
            "말랑아 너는 커서 대단한 뮤지션이 될꺼 같다\n" +
                    "엄마말 들어보니 하루종일 쿵짝쿵짝 하면서 놀았다는데 장난감보다 탬버린을 좋아한다는 우리 말랑이 다치지말고 엄마 괴롭히지 않고 혼자서도 잘 노는 너의 모습에 엄마가 참 좋아한다.\n" +
                    "우리 말랑이 커서 뭐가 되든 상관없으니까 지금처럼 건강하게 자랐으면 아빠는 더 바랄게 없다.",
            "우리 말랑이 노는 사진 좀 더 찍어서 보내주세요 요새는 좀 뜸해ㅋ",86,
            10,
            55,
            "feed_7_4",
            "area",
            2020,
            6,
            11,
            11,20
        )
        insertFeed(7,
            "맘마 먹고 낮잠 자는 네 모습이 아빠를 닮았다며 사진을 보내준 엄마한테 아빠가 나를 닮아서 점점 잘생겨진다고 했더니 엄마가 입을 살짝 벌리고 자는게 나를 닮았다고 하는구나\n" +
                    "우리 말랑이 낮잠자는 모습도 너무 이쁘고 사랑스럽구나\n" +
                    "다자고 일어나면 자꾸 잔소리하는 엄마 좀 괴롭혀줘 요새 아빠를 너무 구박한다",
            "나도 일찍 들어가서 말랑이랑 놀고 싶은데 구박안해도 최대한 일찍들어가는거야 ㅠㅠ  ",
            86,
            10,
            55,
            "feed_7_5",
            "area",
            2020,
            9,
            3,
            11,20
        )
        insertFeed(7,
            "처음으로 비행기를 타고 부산으로 할아버지 보러가는 우리 말랑이. 화장실 가면서 아빠가 없어지니 활짝 웃는 너의 모습을 사진으로 남겼다 말랑아 할아버지 보러가는게 좋은거니 아니면 아빠가 옆에 없는게 좋은거니? 아빠는 그냥 웃는 너의 모습이 좋다 엄마 아빠 힘들게 비행기에서 울지말고 의젓하게 있어 줄꺼지?",
            "이제 우리 말랑이가 나보다 의젓한거 같아.... \n" +
                    "내가 말랑이 닮은 듯 ㅋ ",
            86,
            10,
            55,
            "feed_7_6",
            "age",
            2020,
            9,
            28,
            11,20
        )
        insertFeed(7,
            "간식 들고 있는 엄마만 하염없이 쳐다보는 말랑이 아빠도 좀 봐주면 안되는거니?\n" +
                    "엄마는 많이 혼내고 아빠는 잘놀아주는데 엄마만 바라보고 있는 네가 꼭 연애할 때 아빠랑 닮았구나 말랑아 \n" +
                    "아빠도 간식줄수있는데.....",
            "말랑이가 여보만 너무 좋아하는거 같아서 질투가 나네 \n" +
                    "앞으로 간식은 내가 줄게 나도 간식 줄 수 있게 해줘.. ",
            86,
            10,
            55,
            "feed_7_7",
            "area",
            2020,
            10,
            2,
            11,20
        )


        ///idx:8
        insertFeed(8,
            "재재야 이제 정말 세상밖으로 나올 날이 얼마 안남았어!ㅎㅎ \n" +
                    "오늘도 의사쌤이 재재 건강하게 잘 자라고 있다고 말씀하시더라 \n" +
                    "그나저나 우리 재재는 축구선수가 되려나 댄스가수가 되려나?\n" +
                    "우리 재재 발바닥을 엄마아빠가 오늘 저녁 태교할 때 봤어 너무너무너무 귀엽더라! \n" +
                    "어찌 이런 작은 아이가 엄마 뱃속에 들어왔을까 너무 신기해♥\n" +
                    "언능 우리 재재 보고싶다 멋진 엄마아빠가 되도록 준비하고 있을게 재재야! 곧 만나자^^",
            "이제는 우리가 재재를 만날 날이 얼마 안남았어! 매일 내가 잠들기 전까지 배 쓰담쓰담해주고 밤새 신경써줘서 너무 고마워 곧 노력의 결실인 재재가 나올거야ㅎㅎ 우리 행복한 가족이 되자 여보♥",
            86,
            10,
            55,
            "feed_8_1",
            "area",
            2020,
            12,
            2,
            11,20
        )


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


}