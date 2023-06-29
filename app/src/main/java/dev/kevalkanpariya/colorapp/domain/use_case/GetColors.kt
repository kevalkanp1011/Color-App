package dev.kevalkanpariya.colorapp.domain.use_case

import dev.kevalkanpariya.colorapp.domain.model.Color
import dev.kevalkanpariya.colorapp.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow

class GetColors(
    private val repository: ColorRepository
) {
    operator fun invoke(
    ): Flow<List<Color>> {
        return repository.getColors()
    }
}