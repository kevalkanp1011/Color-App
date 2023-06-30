package dev.kevalkanpariya.colorapp.data.repository

import dev.kevalkanpariya.colorapp.data.data_source.ColorDao
import dev.kevalkanpariya.colorapp.domain.model.Color
import dev.kevalkanpariya.colorapp.domain.model.SyncColorId
import dev.kevalkanpariya.colorapp.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow

class ColorRepositoryImpl(
    private val dao: ColorDao
) : ColorRepository {
    override suspend fun getColors(): Flow<List<Color>> {
        return dao.getNotes()
    }

    override suspend fun getSyncColors(isInSync: Boolean): Flow<List<Color>> {
        return dao.getSyncColors(isInSync = isInSync)
    }

    override suspend fun getColorsForSync(ids: List<Int>): Flow<List<Color>> {
        return dao.getColorsForSync(ids)
    }

    override suspend fun getSyncColorIds(): Flow<List<SyncColorId>> {
        return dao.getSyncColorIds()
    }

    override suspend fun getColorById(id: Int): Color? {
        return dao.getNoteById(id)
    }

    override suspend fun insertColor(note: Color) {
        dao.insertNote(note)
    }

    override suspend fun insertSyncColorId(id: SyncColorId) {
        dao.insertSyncColorId(id)
    }

    override suspend fun insertSyncColor(color: Color) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteColor(note: Color) {
        dao.deleteNote(note)
    }
}