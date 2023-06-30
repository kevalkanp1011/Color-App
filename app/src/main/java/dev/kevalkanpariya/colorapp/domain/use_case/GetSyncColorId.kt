package dev.kevalkanpariya.colorapp.domain.use_case

import dev.kevalkanpariya.colorapp.domain.model.SyncColorId
import dev.kevalkanpariya.colorapp.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow

class GetSyncColorId(
    private val repository: ColorRepository
) {

    suspend operator fun invoke(): Flow<List<SyncColorId>> {
        return repository.getSyncColorIds()
    }
}