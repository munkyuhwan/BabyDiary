package com.anji.babydiary.database.mainFeed

import androidx.room.*
import com.anji.babydiary.database.profile.Profiles


data class FeedWithUser (

    @Embedded
    val feed:MainFeed,
    @Relation(
        parentColumn = "user_idx",
        entityColumn = "idx"
    )
    val userProfile:Profiles


)




