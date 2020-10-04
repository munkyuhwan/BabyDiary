package com.anji.babydiary.shopping.listFragment

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.anji.babydiary.R
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.shopping.ShoppingDatabase
import com.anji.babydiary.database.shopping.shoppingBookmark.ShoppingBookMarkDatabase
import com.anji.babydiary.databinding.ShopListFragmentBinding
import com.anji.babydiary.search.SearchActivity
import com.anji.babydiary.shopping.listFragment.listAdapter.ShoppingListAdapter
import kotlinx.android.synthetic.main.activity_event.*


class ShopListFragment : Fragment() {

    private lateinit var viewModel: ShopListViewModel
    private lateinit var viewModelFactory:ShopListViewModelFactory
    private lateinit var binding:ShopListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().fab.visibility = View.VISIBLE

        binding = DataBindingUtil.inflate<ShopListFragmentBinding>(inflater, R.layout.shop_list_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = ShoppingDatabase.getInstance(application).database
        val bookmarkDatabase = ShoppingBookMarkDatabase.getInstance(application).database

        viewModelFactory = ShopListViewModelFactory(dataSource, bookmarkDatabase, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShopListViewModel::class.java)

        binding.shopModelView = viewModel

        val adapter = ShoppingListAdapter(ProductClickListener {
            Log.e("click", "view clicked")
            //Toast.makeText(application,"${it}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(ShopListFragmentDirections.actionShopListFragmentToShoppingDetail(it))
        },
            ShoppingBookMarkClickListener {
                viewModel.selectBookmark(it)

            },
        requireActivity())
        binding.productList.adapter = adapter

        binding.setLifecycleOwner(this)

        viewModel.allProduct.observe(viewLifecycleOwner, Observer {

            it?.let {
                Log.e("products", "===============================================")
                Log.e("products", "${it}")
                Log.e("products", "===============================================")
                adapter.submitList(it)
            }

        })

        viewModel.allProductCheck.observe(viewLifecycleOwner, Observer {
            if (it.size <= 0) {
                viewModel.saveData(
                    "저희 아이가 이제 젖꼭지를 뗐는데 버리는 것보다 중고물품으로 판매하면 좋을 것 같아서\n" +
                            "이렇게 올립니다~\n" +
                            "살균소독 말끔하게 한 젖꼭지입니다\n" +
                            "색도 맑고 예쁘고 아기들이 정말 좋아할거에요~\n" +
                            "관심있으신 분들은 메시지 보내주세요! \n" +
                            "답장은 조금 늦을 수도 있습니다 ㅠㅠ",
                    "40,000원",
                    "",
                    "second_1",
                    "second")

                viewModel.saveData(
                    "원가 10000원에 구입한 아이용 수건입니다\n" +
                            "갓난아기 침대보로 깔아주셔도 좋고 아이들 목욕수건으로 사용하셔도 좋습니다!\n" +
                            "택배비는 착불입니다! \n" +
                            "메시지 주세요~ ",
                    "3,000원 ",
                    "",
                    "second_2",
                    "second")
                viewModel.saveData(
                    "유축기입니다! 저희 아내가 살균소독을 워낙 깔끔히 해서 위생걱정 붙들어 매셔도 됩니다~\n" +
                            "유축기 사용설명서도 같이 동봉해드릴게요! \n" +
                            "메시지 또는 댓글로 궁금하신 점은 남겨주세요",
                    "5,000원",
                    "",
                    "second_3",
                    "second")

                viewModel.saveData(
                    "공주님들이 좋아하는 꼬까신입니다~><\n" +
                            "사이즈는 130으로 돌아기들이 신기 좋은 사이즈에요! \n" +
                            "돌을 앞둔 아이가 있는 분들에게 가장 추천드립니다! \n" +
                            "돌잔치에서 한껏 예쁨을 발휘할 수 있을거에요~ \n" +
                            "문의는 댓글이나 메시지 부탁드려용!",
                    "8,000원",
                    "",
                    "second_4",
                    "second")
                viewModel.saveData(
                    "흰색신발이라 조금 떼가 탔지만 최대한 깨끗하게 세탁해두었습니다.\n" +
                            "사이즈는 130입니다. 여자아이, 남자아이 모두 잘 어울리는 디자인입니다.\n" +
                            "안감 천이 부드러운 소재로 피부에 무리 없을 것입니다.\n" +
                            "자세한 사항은 메시지로 전달해드릴게요~",
                    "15,000원",
                    "",
                    "second_5",
                    "second")
                viewModel.saveData(
                    "여자아기에게 딱! 안성맞춤인 신발 판매합니다! \n" +
                            "선물 받은 새 제품인데 저희 아이한테는 조금 작아서 필요한 분들을 위해 판매하려 합니다~ \n" +
                            "저도 선물하는 마음으로 무료나눔 하기로 했습니다! \n" +
                            "관심있으신 분들은 댓글이나 메시지 남겨주세요~\n" +
                            "*택배비는 착불입니다!*",
                    "0원",
                    "",
                    "second_6",
                    "second")
                viewModel.saveData(
                    "저희 첫째도 둘째도 한몸처럼 끼고 다니던 애착인형 판매합니다~\n" +
                            "셋째는 계획이 없어서 이제 베프님들에게 중고 나눔 하려합니다!\n" +
                            "둘째까지 사용했는데도 아직 털이 보송보송하고 부드럽습니다! \n" +
                            "제가 틈틈이 살균도 하고 혹시나 눈에는 안보이지만 진드기가 걱정되어 세탁도 꼼꼼히 했답니다 ^^\n" +
                            "그래도 둘째까지 사용했으니 가격은 아주아주 저렴하게 내놓았습니다~\n" +
                            "귀여운 아가들에게 곰돌이 인형 친구 선물해주세요!",
                    "5,000원",
                    "",
                    "second_7",
                    "second")
                viewModel.saveData(
                    "갓난아이들에게 씌우는 모자에요! \n" +
                            "7개월 전으로는 대부분 다 맞을 것 같아요~ \n" +
                            "20,000원에 구매했는데 반값에 세탁까지 깔끔하게 해서 보내드릴게요~\n" +
                            "출산 예정이신 분들은 서둘러 연락주세요 ㅎㅎ",
                    "10,000원",
                    "",
                    "second_8",
                    "second")
                viewModel.saveData(
                    "사진 뒤쪽에 있는 아기배드 판매합니다! 보* 브랜드 제품이구요\n" +
                            "깔끔한 화이트톤에 원목배드라서 범퍼나 접이식보다 튼튼하고 안전합니다ㅎㅎ\n" +
                            "처음 구매했을 때 시험삼아 저도 올라가봤는데 아주 튼튼하더라구요ㅎㅎㅎㅎ\n" +
                            "저는 새제품으로 130,000원에 구매했고 저렴하게 내놓았습니다! \n" +
                            "자세한 정보나 궁금하신 점은 메시지나 댓글 부탁드려요~",
                    "70,000원",
                    "",
                    "second_9",
                    "second")
                viewModel.saveData(
                    "사이즈 50*50 공룡 모양 천 인형입니다!\n" +
                            "제가 직접 만든 아이 애착인형인데 이제 저희아이는 애착인형을 때서 이렇게 판매하기로 했답니다!ㅎㅎ 브랜드물건은 아니지만 아기 전용 천으로 한땀한땀 정성스럽게 만들었습니다!\n" +
                            "저희 아이도 어딜가나 항상 옆에 끼고 다니면서 잘 사용했어요~ \n" +
                            "깨끗~하게 세탁 해놓았으니 관심있으신 분들은 메시지 주세요~",
                    "8,000원",
                    "",
                    "second_10",
                    "second")



                viewModel.saveData(
                    "밤부베베 엠보손수건 추천해요!\n" +
                            "크기도 크고 보들보들해서 정말 좋더라구요\n" +
                            "아기 트름시킬 때 어깨에 받쳐놓으면 옷에도 안묻고\n" +
                            "KC인증, 유해물질 시험통과 된 제품이에요!\n" +
                            "신생아들은 목욕시키고 사용하기에도 끄떡 없어요ㅎㅎ\n" +
                            "사용후 만족감이 너무 큰 물건이라 베프님들한테도 추천하고 싶어요!",
                    "4,000원",
                    "https://smartstore.naver.com/bamboobebe/products/253150695?NaPm=ct%3Dkffbt53c%7Cci%3Df2f93d62ab40a94799fa3f53b72e233d5686bdfb%7Ctr%3Dslsbrc%7Csn%3D253044%7Cic%3D%7Chk%3D474cbda82fc8fbd82ffcc1c7d3a41c54a9428ec9",
                    "recomm_1",
                    "recomm")
                viewModel.saveData(
                    "브라운 체온계 중에 AgeSmart 기능이 들어간 IRT6520(귀적외선체온계) 추천드려요!\n" +
                            "0~3개월, 3~36개월, 36개월 이상으로 구분되어 있어서 연령군 선택한 뒤 체온을 측정하는 온도계에요\n" +
                            "연령별 발열기준이 다른데 그 기준을 모르더라도 아기가 열이 있는지 없는지 확인 할 수 있는 점이 참 좋은 것 같아요! 설명서에도 체온범위 자세히 써있으니 참고하시면 좋을 것 같아요!ㅎㅎ\n" +
                            "아! 구매하실 때 국내 A/S가능한지 꼭 확인하고 구매하셔용!",
                    "4,000원",
                    "https://smartstore.naver.com/braunmall/products/693089378\n",
                    "recomm_2",
                    "recomm")
                viewModel.saveData(
                    "**쿠첸 젖병소독기**\n" +
                            "다른 제품들과는 다르게 소독하는 중간에 문을 여닫아도 소족이 끊기지 않고 이어져요! \n" +
                            "그리고 3단으로 나뉘어 있어서 공간활용이 좋은 것 같아요ㅎㅎ\n" +
                            "저도 출산 후 제일 잘 사용하고 있는 물건입니당>< \n" +
                            "젖병소독기 필요하신 베프분들은 이 아이 참고해보세용!",
                    "4,000원",
                    "https://www.cuchen.com/shopping/product/view.do?pdSeq=566&pcCd=CCAF\n",
                    "recomm_4",
                    "recomm")
                viewModel.saveData(
                    "<일동 후디스 산양분유 추천해요!>\n" +
                            "저희아이 후디스 산양분유2단계 먹이고 있는데 괜찮은 것 같아서 추천해요 ㅎㅎ\n" +
                            "6~24개월까지 먹일 수 있어서 주문하는 것도 나름 편하구요! \n" +
                            "(단계별로 나눠져 있으니 꼭 확인해보세용!)\n" +
                            "추천이유 중 하나가 우유 대비 아기변 및 장 건강에 중요한 올리고당이 10배 정도 많이 함유되어 있고 카제인 단백질이 적어서 아기들이 부드럽게 소화 흡수 할 수 있다는 점이에요!^^\n" +
                            "저희 아이도 확실히 분수토 하는 횟수가 점점 줄면서 좋아지는 걸 느꼈어용 \n" +
                            "모유와 산양유는 동일한 유즙분비 방식으로 아이 건강에 중요한 생리 활성성분이 들어있어서 안심하시고 먹여도 좋을 것 같아요~ 베프님들에게 추천 꾹~ 합니다 ><",
                    "4,000원",
                    "http://www.foodismall.com/GoodsDetail.mi?goodsseq=49818",
                    "recomm_5",
                    "recomm")
                viewModel.saveData(
                    "★ 비오 기저귀 교환패드 ★\n" +
                            "다들 아시는 제품이겠지만 호옥시나 이제 막 육아에 입문하신 초모 베프들에게 알립니다! \n" +
                            "비오 기저귀 교환패드 적극 추천드려요! ㅎㅎ\n" +
                            "100%순면, FDA등록 소재, 한국 로하스 인증소재, 순수 국내 제작상품에다가 국내 유일한 순명 일회용 다용도 패드랍니다 >< 그리구 유일하게 식양처가 관리하는 위생용품 테스트에 통과했어요!!\n" +
                            "저희 아이는 클래식으로 썼었는데 방수기능도 굉장히 탁월해서 어디서든지 유용하게 쓰이고있어요 ><\n" +
                            "클레이도 잘 떨어지고 물감놀이에도 사용할 수 있는 건 꿀팁입니당ㅎㅎㅎㅎㅎ \n" +
                            "링크 걸어둘게요~",
                    "4,000원",
                    "https://smartstore.naver.com/bo/products/2429185121",
                    "recomm_3",
                    "recomm")
            }
        })

        binding.appCompatImageView.setOnClickListener {

            val intent: Intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivityForResult(intent, CommonCode.SEARCH_KEYWORD)
        }

        val manager = GridLayoutManager(activity,2)
        binding.productList.layoutManager = manager

        //val spacingInPixels = resources.getDimensionPixelSize(R.dimen.recycler_view_item_width)
        //binding.productList.addItemDecoration(SpacesItemDecoration(spacingInPixels))


        binding.writeProduct.setOnClickListener {
            
            findNavController().navigate(ShopListFragmentDirections.actionShopListFragmentToWriteProduct(1))
        }

        viewModel.bookMarks.observe(viewLifecycleOwner, Observer {
            it?.let {
                for (i in it.iterator()) {
                    viewModel.selectBookmarkedFeed(i.shoppingIdx)
                }
            }
        })

        viewModel.seletBookMark.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size <= 0) {
                    viewModel.addBookmark(viewModel.bookmarkTipIdx)
                }else {
                    viewModel.deleteBookmark(viewModel.bookmarkTipIdx)
                }

            }
        })

        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CommonCode.SEARCH_KEYWORD && resultCode == Activity.RESULT_OK) {

            data?.let {
                it.extras?.let {
                    viewModel.selectByKeyword(it.get("keyword").toString())
                }
            }

        }



    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShopListViewModel::class.java)
    }

}

class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }

}
