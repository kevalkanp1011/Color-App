package dev.kevalkanpariya.colorapp.domain.repository

import dev.kevalkanpariya.colorapp.domain.model.Color
import kotlinx.coroutines.flow.Flow

interface ColorRepository {

    fun getColors(): Flow<List<Color>>

    suspend fun getColorById(id: Int): Color?

    suspend fun insertColor(note: Color)

    suspend fun deleteColor(note: Color)
}