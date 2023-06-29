package dev.kevalkanpariya.colorapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.kevalkanpariya.colorapp.domain.model.Color

@Database(
    entities = [Color::class],
    version = 1
)


abstract class ColorDatabase: RoomDatabase() {

    abstract val noteDao: ColorDao

    companion object{
        const val DATABASE_NAME = "colors_db"
    }

}
