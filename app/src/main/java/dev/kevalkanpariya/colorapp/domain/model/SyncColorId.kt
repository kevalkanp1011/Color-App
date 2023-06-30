package dev.kevalkanpariya.colorapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SyncColorId(
    val id: Int,
    @PrimaryKey val key: Int? = null
)
