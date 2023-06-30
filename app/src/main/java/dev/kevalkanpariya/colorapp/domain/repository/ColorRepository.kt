package dev.kevalkanpariya.colorapp.domain.repository

import dev.kevalkanpariya.colorapp.domain.model.Color
import dev.kevalkanpariya.colorapp.domain.model.SyncColorId
import kotlinx.coroutines.flow.Flow

interface ColorRepository {

    suspend fun getColors(): Flow<List<Color>>

    suspend fun getSyncColors(isInSync: Boolean): Flow<List<Color>>

    suspend fun getColorsForSync(ids: List<Int>): Flow<List<Color>>

    suspend fun getSyncColorIds(): Flow<List<SyncColorId>>

    suspend fun getColorById(id: Int): Color?

    suspend fun insertColor(note: Color)

    suspend fun insertSyncColorId(id: SyncColorId)

    suspend fun insertSyncColor(color: Color)

    suspend fun deleteColor(note: Color)
}