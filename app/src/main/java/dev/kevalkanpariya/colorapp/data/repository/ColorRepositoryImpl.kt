package dev.kevalkanpariya.colorapp.data.repository

import dev.kevalkanpariya.colorapp.data.data_source.ColorDao
import dev.kevalkanpariya.colorapp.domain.model.Color
import dev.kevalkanpariya.colorapp.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow

class ColorRepositoryImpl(
    private val dao: ColorDao
) : ColorRepository {
    override fun getColors(): Flow<List<Color>> {
        return dao.getNotes()
    }

    override suspend fun getColorById(id: Int): Color? {
        return dao.getNoteById(id)
    }

    override suspend fun insertColor(note: Color) {
        dao.insertNote(note)
    }

    override suspend fun deleteColor(note: Color) {
        dao.deleteNote(note)
    }
}