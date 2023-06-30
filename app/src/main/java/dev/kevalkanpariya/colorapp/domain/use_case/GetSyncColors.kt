package dev.kevalkanpariya.colorapp.domain.use_case

import dev.kevalkanpariya.colorapp.domain.model.Color
import dev.kevalkanpariya.colorapp.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow

class GetSyncColors(
    private val repository: ColorRepository
) {

    suspend operator fun invoke(isInSync: Boolean): Flow<List<Color>> {
        return repository.getSyncColors(isInSync = isInSync)
    }
}