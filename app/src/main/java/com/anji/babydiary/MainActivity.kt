package com.anji.babydiary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.databinding.ActivityMainBinding
import com.anji.babydiary.login.Login
import com.anji.babydiary.mainFeed.MainFeedActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.lang.Runnable
import java.util.*
import kotlin.system.exitProcess

class MainActivity : BaseActivity() {
    //var job = Job()
    //var uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)


        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        //month -1
        /*
        val expDate = GregorianCalendar(2020, 8, 9) // midnight
        val now = GregorianCalendar()

        val isExpired = now.after(expDate)

        if (isExpired) {
            Toast.makeText(this, "사용기간이 만료되었습니다.", Toast.LENGTH_SHORT).show()
            finishAffinity()
            finish()
            exitProcess(-1)
        }
        */

        doInsert(1)
        doInsert(2)
        doInsert(3)
        doInsert(4)
        doInsert(5)
        doInsert(6)
        doInsert(7)
        doInsert(8)


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
            1)
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
            15)
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
            5)

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
            1)
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
            30)

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
            28)

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
            3)

        uiScope.launch {
             delay()
        }

    }




    suspend fun delay() {
        withContext(Dispatchers.IO) {
            Thread.sleep(3000)
            goMain()
        }
    }

    private fun goMain() {
        //CommonCode.USER_IDX = 1
        val intent:Intent = Intent(this, Login::class.java)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        startActivity(intent)
        finish()
    }

}