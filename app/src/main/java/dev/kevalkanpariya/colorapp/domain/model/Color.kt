package dev.kevalkanpariya.colorapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Color(
    val timestamp: String,
    val color: Int,
    @PrimaryKey val id: Int? = null
)