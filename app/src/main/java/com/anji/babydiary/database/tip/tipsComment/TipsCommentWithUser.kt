package com.anji.babydiary.database.tip.tipsComment

import androidx.room.Embedded
import androidx.room.Relation
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.tip.Tips


data class TipsCommentWithUser(
    @Embedded
    val tips:TipsComment?,
    @Relation(
        parentColumn = "user_idx",
        entityColumn = "idx"
    )
    val prof:Profiles?

)