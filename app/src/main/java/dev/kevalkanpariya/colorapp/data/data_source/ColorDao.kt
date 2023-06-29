package dev.kevalkanpariya.colorapp.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.kevalkanpariya.colorapp.domain.model.Color
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {

    @Query("SELECT * FROM color")
    fun getNotes(): Flow<List<Color>>

    @Query("SELECT * FROM color WHERE id = :id")
    suspend fun getNoteById(id: Int): Color?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertNote(note: Color)

    @Delete
    suspend fun deleteNote(note: Color)
}