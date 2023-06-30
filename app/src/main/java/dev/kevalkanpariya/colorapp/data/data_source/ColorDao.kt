package dev.kevalkanpariya.colorapp.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.kevalkanpariya.colorapp.domain.model.Color
import dev.kevalkanpariya.colorapp.domain.model.SyncColorId
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {

    @Query("SELECT * FROM color")
    fun getNotes(): Flow<List<Color>>

    @Query("SELECT * FROM color WHERE isInSync = :isInSync")
    fun getSyncColors(isInSync: Boolean): Flow<List<Color>>

    @Query("SELECT * FROM color WHERE id IN (:itemIds)")
    fun getColorsForSync(itemIds: List<Int>): Flow<List<Color>>

    @Query("SELECT * FROM synccolorid")
    fun getSyncColorIds(): Flow<List<SyncColorId>>

    @Query("SELECT * FROM color WHERE id = :id")
    suspend fun getNoteById(id: Int): Color?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertNote(note: Color)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncColorId(id: SyncColorId)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncColor(color: Color)

    @Delete
    suspend fun deleteNote(note: Color)
}