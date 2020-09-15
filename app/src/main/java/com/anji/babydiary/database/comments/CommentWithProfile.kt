package com.anji.babydiary.database.comments

import androidx.room.Embedded
import androidx.room.Relation
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.tip.Tips

/*
data class CommentWithProfile(
    @Embedded
    val comment: Comments?,
    @Relation(
        parentColumn = "user_idx",
        entityColumn = "idx"
    )
    val prof: Profiles?

)
 */