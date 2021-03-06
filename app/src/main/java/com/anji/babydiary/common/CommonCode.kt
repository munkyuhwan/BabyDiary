package com.anji.babydiary.common

import androidx.lifecycle.LifecycleOwner
import java.lang.reflect.Array
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object CommonCode {

    val PERMISSION_CODE = 9898
    val IMAGE_PICK_CODE = 1000
    val MYPAGE_ACTIVITY_RESULT = 1100
    val SEARCH_KEYWORD = 9999

    val SENDER_ID = "367403351297"

    val TIP_CATEGORY = arrayOf(
        "건강",
        "놀이",
        "교육",
        "기타"
    )

    val TIP_DATA_USER = arrayOf(
        1L,4L,5L,2L,4L,5L,7L,5L,4L,3L,4L,2L,3L,4L,1L,2L,3L,4L
    )
    val TIP_DATA_CAT = arrayOf(
        "건강",
        "건강",
        "교육",
        "교육",
        "교육",
        "놀이",
        "놀이",
        "놀이",
        "놀이",
        "놀이",
        "기타",
        "기타",
        "기타",
        "기타",
        "기타",
        "기타",
        "기타",
        "기타",
        "기타"
    )
    val TIP_DATA_IMG = arrayOf(
        "tip_01",
        "tip_09",
        "tip_06",
        "tip_07",
        "tip_08",
        "tip_14",
        "tip_10",
        "tip_12",
        "tip_11",
        "tip_13",
        "tip_16",
        "tip_17",
        "tip_05",
        "tip_04",
        "tip_18",
        "tip_02",
        "tip_03",
        "tip_15"
    )
    val TIP_DATA_TXT = arrayOf(
        "0~6개월 신생아 노란딱지 관리법!\n" +
                "두피각질을 불린다\n" +
                "미온수에 적신 수건으로 모발 및 두피 전체에 걸쳐 가볍에 찜질해주세요\n" +
                "2. 두피층과 각질, 기름층 분리 오일마사지\n" +
                "호호바오일, 헤이즐넛 오일이 두피진정 및 피부보호에 탁월합니다!\n" +
                "3. 미온수에 샴푸로 마무리\n" +
                "4. 두피염진정용 크림사용 (지루성두피크림, 아기 지루선 두피염 전용)\n" +
                "\n" +
                "Q. 무료로 영유아 건강검진 받을 수 있나요?\n" +
                "A. 영유아 건강검진은 생후 4개월부터 71개월(만6세)까지의 영유아를 대상으로 하는 성장 단계별 건강검진 프로그램으로, 개인 비용 부담 없이 받을 수 있다.\n" +
                "영유아의 성장과 발달특성을 고려해 단계별로 건강검진을 지원 받게 된다. \n" +
                "검진기관으로 지정된 인근 병·의원이나 보건기관에서 영유아건강검진과 구강검진을 받는다.",
        "개월별 예방접종을 참고하세요!\n",
        "▶ 연령과 시기별 훈육법 1단계 ◀\n" +
                "아이가 스스로 행동수정이 어렵고, 양육자가 설명해도 알아듣지 못하는 시기\n" +
                "아주 짧게 “~하고 싶었어?” / “~해서 그건 안되고, 이렇게 해보자!”\n" +
                "곧바로 아이 흥미를 다른 곳으로 돌려주기\n" +
                ": 이 시기에는 곧바로 해줄 것이 아니면 짧게 설명하고 아이를 번쩍 들어서 그 공간을 벗어날 것을 추천해요!",
        "▶ 연령과 시기별 훈육법 2단계 ◀\n" +
                "아이가 스스로 행동수정은 어렵지만, 설명했을 때 알아듣고 대답할 수 있는 시기\n" +
                "아이가 감정컨트롤이나 행동이 일관성 있는 시기가 아니기 때문에 어떨 때는 잘하고 어떨 때는 애원해도 듣지 않을 때가 있습니다. \n" +
                "1단계에 비해 인내심이 더 필요!\n" +
                "“~해서 ~하고 싶었어? 속상해서 울고 화내는 거야 ?” 까지 감정을 수용하고\n" +
                "“그런데 ~해서 이건 해줄 수 없어. 대신 ~는 괜찮아?” 라고 대안제시까지 해주기\n" +
                "* 고려할 것 *\n" +
                "안전과 질서 확인하기 : 최대한 안전하고 피해주지 않는 장소로 이동 후, 아이가 스스로 감정을 조정하는 연습을 할 수 있게 기회를 주어야합니다. 단호한 표정으로 침묵을 유지해야 해요.\n",
        "▶ 연령과 시기별 훈육법 3단계 ◀\n" +
                "양육자와 상호작용이 가능하고, 스스로 행동수정이 가능한 시기\n" +
                "아이의 감정도 중요하지만, 양육자의 감정도 전달하도록 합니다\n" +
                "자신의 행동으로 인해 다른 사람의 감정이 어떤지 관심 갖도록 훈련할 것!\n" +
                "대안은 스스로 찾기\n" +
                "“그 것 말고 다른 거 어떤 걸로 대신하면 좋을까?” 라고 잘못된 행동은 정확히 짚어주고, 양육자가 대안을 제시하는 것이 아닌 아이가 스스로 대안 찾기를 시도해보도록 하는 것입니다.\n" +
                "이때 아이가 제시한 대안은 안전에 위배되지 않는다면 되도록 수용을 해주도록 합니다.",
        "월령별 놀이장난감 고르기\n" +
                "색상대비가 강한 장난감\n" +
                "갓태어난 아기는 색상대비가 강한 흑백이나 가까이 있는 불빛 정도를 볼 수 있다.\n" +
                "흰색이나 파스텔 컬러 장난감보다는 색깔 대비가 강한 장난감을 고르는 것이 요령\n" +
                "2. 누르면 소리가 나는 장난감\n" +
                "생후 6개월이 넘으면 방바닥을 기어다니면서 온 집안을 탐색하기 시작한다.\n" +
                "아기가 끌고 다니며 놀 수 있는 장난감이나 누르며 소리를 내고 반응하는 장난감이 좋다.\n" +
                "3. 유대감을 느낄 수 있는 장난감\n" +
                "돌을 전후하여 걷기 시작하고, 장난감을 갖고 놀면서 유대감을 키워나간다.\n" +
                "간단한 말과 감정표현을 시작하면서 소유의식이 커지므로 유대감을 느낄 수 있는 장난감이 좋다.\n" +
                "4. 창의력과 사회성을 길러주는 장난감\n" +
                "악기 장난감 등으로 창의력을 키워주고 가족, 친구와 함께 놀이를 즐기면서 장난감을 공유하고 같이 노는 법을 알게해서 사회성을 길러주는 것이 베스트!",
        "▶오감 자극하는 실내 촉감놀이 – 곡물놀이편◀\n" +
                "콩, 쌀, 옥수수로 노는 곡물놀이\n" +
                "- 콩이나 옥수수를 가지고 놀 때 주의 점은 호기심이 많은 아이들이 콩이나 옥수수를 코에 넣  지 않도록 미리 아이들과 함께 이야기하면서 약속을 정하고 시작하세요\n" +
                "- 놀이를 하면서 쌀을 만질 때의 느낌과 콩, 옥수수를 만질 때의 느낌을 비교해 볼 수 있게   질문을 던져보고 소리나 냄새, 색깔의 차이를 직접 감각으로 구분해 볼 수 있게 유도해주세  요 :)\n" +
                "- 쌀놀이 같은 경우, 빨대깃발을 꽂아서 넘어지지 않게 쌀을 누가 더 많이 가져오는지 게임으  로 즐겨보면 더욱 재미있는 놀이가 될거에요!\n",
        "▶오감 자극하는 실내 촉감놀이 – 밀가루, 밀가루 반죽놀이편◀\n" +
                "밀가루 반죽 만드는 방법\n" +
                "밀가루에 물, 식용유, 소금을 넣고 반죽을 하면 좀더 오래 갖고 활동 할 수 있어요\n" +
                "물감을 조금씩 섞어서 알록달록 시각적 효과도 준다면 더없이 좋은 촉감놀이 재료가 되겠죠?\n" +
                "밀가루 반죽놀이 TIP!\n" +
                "병뚜껑, 레고, 단추, 끈, 조개껍데기, 쿠키들 들의 생활 속 도구를 밀가루 반죽놀이에 활용해보세요! 그리고 ‘밀대’로 밀고 소꿉놀이용 칼로 국수도 잘라볼 수 있게 해주세요.\n" +
                "도구 하나로 아이들이 역할놀이도 하고 소꿉놀이도 하면서 놀이가 확장 될 수 있습니다.",
        "▶오감 자극하는 실내 촉감놀이 – 미역놀이편◀\n" +
                "미역은 건조되었을 때와 물에 불렸을 때의 촉감과, 색깔의 변화가 큰 재료입니다 \n" +
                "그래서 아이들이 더욱 호기심 있게 관찰하며 놀이 할 수 있는 자연재료이지요!\n" +
                "미역놀이를 즐길때에는 욕조에서 활동하면 정리걱정 없고 물과 미역이 만날 때 변화되는 차이를 직접 눈으로, 손의 감각으로 확인할 수 있어요\n" +
                "미역놀이 TIP!\n" +
                "욕조에 소꿉놀이 세트와 함께 제시해주면 새로운 놀이로의 확장이 가능해요!",
        "▶오감 자극하는 실내 촉감놀이 – 클레이 점토놀이편◀\n" +
                "숫자나 알파벳 찍기틀을 이용해서 글자 맞추기나 글자 만들기 놀이를 해보세요\n" +
                "점토놀이 찍기 틀이나 쿠키틀, 칼도구, 송곳도구, 밀대 등을 이용해서 활용해보세요\n" +
                "어린아이들의 경우 점토를 그냥 사용할 때 보다 찍기틀이나 도구를 이용하면 모양을 나타내기에 수월할 수 있습니다",
        "이유식거부 대처법5가지!\n" +
                "놀이식판으로 바꿔보기\n" +
                "반복되는 것을 싫어하는 아이는 식사 시, 호기심을 끌 수 있는 것을 준비해봐요!\n" +
                "화려한 놀이식판을 사용하면 식사시간동안 다른 곳을 보는 아이가 식판에 집중할거에요\n" +
                "2. 고기 잡내 쏙 빼주기\n" +
                "아기들은 무(無)맛인 분유를 위주로 섭취했기 때문에 고기 잡냄새에 민감해요\n" +
                "철분섭취를 위해 고시 양을 늘린 후 이유식 거부가 시작되었다면 육수에 양파, 무를 넣어 잡냄새만 잡아줘도 거부가 줄어들어요 :)\n" +
                "3. 이유식 간 해보기\n" +
                "입맛을 잡는다고 간이 없는 이유식을 선호하는 부모가 많지만,\n" +
                "돌 이후로 잼발라 후리가케 정도는 소량 첨가해주세요.\n" +
                "면역력이 크는 시기이므로 입맛보다는 식사가 더 중요하답니다 ^^\n" +
                "4. 식사 전 유산균 먹여보기\n" +
                "장 유해균 때문에 변비, 소화불량이 생겨 밥을 거부하는 아이도 많습니다.\n" +
                "식사 30분 전 유산균을 물에 타 먹여주면 변비와 소화불량에 도움을 줘 거부의 원인을 잡을 수 있어요!\n" +
                "5. 기대감 낮추기\n" +
                "아기가 먹지 않더라도 너무 실망하는 티를 내지 않는게 좋아요\n" +
                "엄마의 조급한 마음은 아이의 스트레스가 될 수 있습니다 ㅠㅠ\n" +
                "아이가 조금만 먹더라도 ‘언젠간 먹겠지..!’마인드가 가장 중요해요!",
        "사람들이 잘 모르는 임신 중 반드시 조심해야할 5가지!\n" +
                "펌/염색 : 펌이나 염색을 할 때, 약이 두피를 통해 혈액으로 전달될 수 있음!\n" +
                "   펌이나 염색약이 화학제품이니 되도록 하지 않는 것이 좋아요\n" +
                "2. 참치통조림 : 체내 수은이 일정량 이상 쌓이게 되면 태아 신경계 발달에 영향을 줄 수 있기 때문에       섭취에 신경써야함! 일주일에 100g이상 섭취하지 않도록 주의해야해요\n" +
                "3. 초콜릿 / 커피 / 홍차 : 캔커피는 74mg, 초콜릿 30g에는 16mg, 커피우유에는 47mg이 들어있다고 함. 카페인 섭취의 일일 권장량을 넘기지 않도록 주의해요!\n" +
                "4. 식혜 : 식혜를 만들 때 엿기름이 들어가는데 유즙의 분비를 방해하는 성분이 포합되어 있어요.\n" +
                "모유수유를 계획하고 있다면 임신중에도, 출산 후 수유기간에도 주의해야해요!\n" +
                "5. 알로에/녹두 : 알로에는 기를 끌어내리는 기운이 강하고 녹두는 몸을 차게하고 소염작용이 강해 임산      부에게 좋지 않음! 임산부를 몸이 따듯해야 혈액순환이 잘되고 태아의 성장에 도움이 되요",
        "★애착인형이 꼭 필요한 이유★\n" +
                "분리불안 완화 \n" +
                "생후 6개월 이후부터 유아분리불안증을 잘 넘기기 위해 필요한 애착인형.\n" +
                "함께 있어주는 존재로 인식해 아기에게 마음의 안정을 준다.\n" +
                "오감발달\n" +
                "만지고, 보고, 얘기하면서 오감을 발달시키는데 도움을 준다.\n" +
                "3. 사회성발달\n" +
                "36개월 이전에는 혼자노는 시간이 많은데, 역할놀이를 하면서 사회성을 기르는데 도움을 준다.\n" +
                "※ 만약, 생후 36개월 이후에도 애착인형에 대한 집착이 심하더라도 강제로 떼어내기보다 스스로 바뀌기를 충분히 기다려주세요 ※",
        "☆애착인형이 고르는 꿀TIP☆\n" +
                "따듯함과 위안을 주는 부드러운 소재\n" +
                "만졌을 때 푹신하고, 밝고 포근한 색상\n" +
                "들고 다니기 좋은 50cm안팎의 크기\n" +
                "피부를 자극하지 않는 안전한 소재 (ex. 면100%)\n" +
                "바느질이 잘 되어있는 튼튼한 인형",
        "▶▷출산 입원 준비물 준비하기◁◀\n" +
                "1. 보온용내의, 목이 긴 양말\n" +
                "2. 수유브라, 패드팬티, 팬티 : 넉넉히 2~5장 준비해주세요. 제왕절개 산모는 절개선 위까지 덮을   수 있는 사이즈가 좋습니다 :)\n" +
                "3. 수건 : 수건은 세면뿐 아니라 온찜질, 유방 마사지 들에 요긴하게 쓰이니 넉넉하게!\n" +
                "4. 배넷저고리, 속싸게, 겉싸게 : 아기에게 입힐 것도 함께 준비해주세요\n" +
                "5. 보온병 : 매번 정수기까기 가지 않아도 되어 편리해요!\n" +
                "6. 복대 : 출산 후 이완 된 허리와 배등을 조여주기 위해 필요해요",
        "▶▷아기 키우는 아빠들의 필수 육아팁 9가지 - 1탄◁◀\n" +
                "조기 신체접촉이 중요!\n" +
                "엄마에 비해 아빠는 상대적으로 아이와 친해질 계기가 없다.\n" +
                "의도적으로 아이와 신체접촉을 해야 유대감을 키울 수 있다.\n" +
                "2. 아기가 운다고 물러서지 마세요\n" +
                "아기가 우는 것은 가장 유일한 의사표현이다.\n" +
                "'아기가 왜 울까'라는 질문을 계속해가며 모든 것에 이유가 있다는 관점으로 접근해야 한다. 울음을 이해하는 것은 소통의 시작이다.\n" +
                "3. 아기 체온이 37도? 당황하지 말고..\n" +
                "아기의 정상 체온은 37℃ 전후이니 놀랄 필요는 없다. \n" +
                "열이 있어도 아기가 잘 놀면 옷을 가볍고 시원하게 입히고, 미지근한 물로 닦아 주면서 체온의 변화를 확인한다.\n" +
                "4. 아기 재우기는 필수과목\n" +
                "아이가 혼자 잠들게 하려면 완전히 잠들 때까지 안거나 젖을 물리지 말고, 졸리지만 아직 깨어있는 상태로 잠자리에 눕힌다.\n" +
                "5. 아이 따로 재우는 시기는?\n" +
                "4~6개월 사이에 아이를 따로 재우는 시도를 할 수 있다.\n" +
                "여기서 중요한 점은 이 기간 동안 따로 재우기를 하지 못한 경우에는 만 3세 이후에 다시 시도 해야한다는 점이다.",
        "▶▷아기 키우는 아빠들의 필수 육아팁 9가지 – 2탄◁◀\n" +
                "6. 안아주고 말 태워주세요\n" +
                "생후 12개월이 되기 전에 아빠가 아이를 많이 안나주거나 말을 태워주는 등 피부접촉을 많이 하면 아이는 아빠에게서 애정을 듬뿍 받는다고 느끼고 이를 평생 기억한다. 또 몸을 움직이는 놀이를 해주면 신체 기능뿐 아니라 뇌 기능까지 발달한다.\n" +
                "7. 꾸중할 때는 어떻게? 이렇게!\n" +
                "아이가 실수를 했다면 \"다치지 않았니? 놀라지는 않았니? 조심해야겠구나\" 등의 말로 아이를 먼저 안심시킨다. 그리고 아이가 고의로 잘못을 했을 경우에는 잘못한 행동만을 지적하고 감정적으로 대하지 않는다.\n" +
                "8. 훈육도 결국은 소통이랍니다\n" +
                "일방적인 훈육을 강행하면 아이는 스트레스 호르몬인 코티졸이 나오고 이 상태에서는 새로운 것을 받아들이는 것이 어려워 행동 개선을 기대하기는 어렵다.\n" +
                "9. 책임감과 자립심 키우는 가사분담\n" +
                "집안일을 아이가 잘 했다고 해서 돈이나 선물 등의 보상을 처음부터 제시하면 안 된다. \n" +
                "집안일은 내가 당연히 해야 하는 내 일이고, 가족을 위한 일이라는 인식을 먼저 심어줄 필요가 있다.",
        "★ 유축기 대여는 보건소에서! ★\n" +
                "관할 주소지에 해당되는 보건소에 3~4일 전 미리 연락해 예약을 하면 되는데요, 대여기간은 각 보건소마다 다르므로 보건소 홈페이지나 전화예약 시 확인해보시는 것이 좋아요.\n" +
                "대여시, 신분증과 출산 확인서, 산모수첩을 지참하시면 된답니다 ><\n" +
                "* 보유수량이 정해져있어 1인 1회로 제한되는 경우가 대부분이니 참고해주세요! *"
    )


    val INTRODUCTION = arrayOf(
        "아기의 움직임이 활발해지면서 \n" +
                "안전사고에 유의해야 합니다.\n" +
                "손 근육이 발달했으니, \n" +
                "오늘은 혼자 숟가락질 하는 방법을\n" +
                "천천히 알려줘보는 것은 어떨까요?\n" +
                "서투르고 입에 집어 넣는게 대다수지만, \n" +
                "크레파스 끄적이기 활동도 좋은 방법이에요!",
        "아이가 스스로 해보려는 것이 많아지지만,\n" +
                "아직 힘을 조절 할 능력이 부족하고 의사를 표현하기 힘들어\n" +
                "공격적인 행동으로 보일 수 있어요.\n" +
                "신체발달과 조절 능력, 언어 발달 속도의 부조화 때문이므로\n" +
                "아기의 행동에 화를 내고 다그치기 보다\n" +
                "부드럽게 아기를 격려하고 진정 시켜보는 것은 어떨까요?",
        "아이의 발달 속도는 개인차가 심해요.\n" +
                "우리아이는 건강하게 잘 자라고 있으니\n" +
                "느리다는 걱정은 잠시 접어두고\n" +
                "아이가 속도를 맞춰나갈 수 있도록 격려하고 응원해주세요!",
        "\"엄마의 삶은 그 누구도 대신할 수 없는 가치로운 삶이에요.\"\n" +
                "\n" +
                "산후 우울감은 호르몬의 변화로 인해 일어나는 일이므로\n" +
                "당연한 일로 받아들여주세요.\n" +
                "현재의 나의 모습에 무기력하고 의미가 없다면\n" +
                "조금 더 멀리 바라보고 그그로의 마음을 다독여주세요.\n" +
                "지금 모습만으로도 충분히 아름답고 \n" +
                "가치있는 내면의 모습을 바라봐주세요.",
        "아이와 함께 밖에 나가 햇빛을 쐬는 것은\n" +
                "기분을 좋게 해주는 것은 물론이고 아이의 면역력 증진에도,\n" +
                "에너기 발산에도 긍정적인 효과가 있어요.\n" +
                "동네 놀이터나 키즈카페, 육아 종합 지원센터 등\n" +
                "놀이시설에 가보거나 산책을 해보는 것은 어떨까요?"
    )



}