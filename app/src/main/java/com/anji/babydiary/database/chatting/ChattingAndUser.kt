package com.anji.babydiary.database.chatting

import androidx.room.Embedded
import androidx.room.Relation
import com.anji.babydiary.database.profile.Profiles

data class ChattingAndUser (

    @Embedded
    val chatting:Chatting,
    @Relation(
        parentColumn = "user_idx_one",
        entityColumn = "idx"
    )
    val profile:Profiles
)