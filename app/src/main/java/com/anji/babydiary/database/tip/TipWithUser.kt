package com.anji.babydiary.database.tip

import androidx.room.Embedded
import androidx.room.Relation
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.tip.Tips

data class TipWithUser(
    @Embedded
    val tips: Tips?,
    @Relation(
        parentColumn = "user_idx",
        entityColumn = "idx"
    )
    val profile: Profiles?

)