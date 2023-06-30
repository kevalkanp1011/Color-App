package dev.kevalkanpariya.colorapp.domain.use_case

import dev.kevalkanpariya.colorapp.domain.model.Color
import dev.kevalkanpariya.colorapp.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow

class GetColorsForSync(
    private val repository: ColorRepository
) {
    suspend operator fun invoke(
        ids: List<Int>
    ): Flow<List<Color>> {
        return repository.getColorsForSync(ids)
    }
}