package com.anji.babydiary.event.eventList

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.database.event.Event
import com.anji.babydiary.database.event.EventDao
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class EventListViewModel(val database:EventDao, application: Application) : AndroidViewModel(application) {

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)
    var selectedImage = MutableLiveData<Uri>()

    var selectAll = MutableLiveData<List<Event>>()
    var checkResult = database.selectCheckAll()

    init {
        uiScope.launch {
            queryAll()
        }

    }

    suspend fun queryAll() {
        withContext(Dispatchers.IO) {
            selectAll.postValue( database.selectAll() )
        }

    }



    fun doInsert() {

        insertData(
            "img_1",
            "100일 일기 챌린지",
            "100일동안 우리아이 육아일기 쓰고 포토북 받자!\n" +
                    "100일 육아일기를 작성하면 추첨을 통해 ‘포토북’을 만들어드려요~\n" +
                    "우리아이의 성장을 기록하고 오래도록 추억으로 남길 수 있는 절호의 찬스! \n" +
                    "게시물작성 시, ‘#100일일기챌린지_0일’을 태그해주세요!\n" +
                    "\n" +
                    "참여방법 : 게시물 작성시 ‘#100일일기챌린지_0일’을 태그해주세요! (ex. #100일일기챌린지_3일)\n" +
                    "상품 : 100일 일기 챌린지를 달성하"
        )

        insertData(
            "img_2",
            "말풍선을 채워주세요!",
            "아빠가 육아를 하던 줄 아이가 갑자기 보채기 시작하더니 울음을 끊이지 않고 있어요!\n" +
                    "아이가 무슨 말을 하고 싶은 걸까요? \n" +
                    "\n" +
                    "- 참여방법 : 어떤 상황인지 자유롭게 유추해서 말풍선에 들어갈 아이의 말을 댓글로 남겨주세요!\n" +
                    "상품 : 추첨을 통해 10분에게 스*벅스 기프티콘을 드려요~\n" +
                    "이벤트기간 : ~ 9.30까지"
        )

        insertData(
            "img_3",
            "키워드 이벤트",
            "이번 키워드는 “꼬물”♥\n" +
                    "키워드를 연상시키는 사진을 올리고 이벤트를 태그하면 푸짐한 경품이 와르르!\n" +
                    "우리아이의 꼬물스런 장면을 포착해보세요!\n" +
                    "\n" +
                    "참여방법 : 키워드를 연상시키는 게시물을 업로드하고 ‘#키워드이벤트_꼬물’을 태그해주세요!\n" +
                    "상품 : 공감 수에 따라 상품을 드려요!\n" +
                    "1등(1명) >>> 영유아 용품 상품권 100,000원\n" +
                    "2등(3명) >>> 영유아 용품 상품권 50,000원\n" +
                    "3등(10명) >>> 스*벅스 아메리카노(ICE) 기프티콘\n" +
                    "- 이벤트기간 : ~12월 08일까지 "
        )
    }


    fun insertData(image:String, title:String, text:String ) {
        uiScope.launch {
            var event = Event()
            event.title = "${title}"
            event.text = "${text}"
            event.imgDir = "${image}"
            insert(event)
        }
    }

    suspend fun insert(event:Event) {
        withContext(Dispatchers.IO) {
            database.insert(event)
        }
    }

    fun deleteData() {
        uiScope.launch {
            delete()
        }
    }
    suspend fun delete() {
        withContext(Dispatchers.IO) {
            database.deleteAll()
        }
    }

    fun onImageSelected(data: Intent?) {
        selectedImage.value = data?.data!!
        //insertData()
    }

    fun selectByKeyword(keyword:String){
        uiScope.launch {
            queryKeyword(keyword)
        }
    }

    suspend fun queryKeyword(keyword:String) {
        withContext(Dispatchers.IO) {
            selectAll.postValue( database.selectByKeyword(keyword) )
        }
    }

}


class EventListClickListener(val clickListener:(eventIdx:Long)->Unit) {
    fun onEventClick(event: Event) = clickListener(event.idx)
}

