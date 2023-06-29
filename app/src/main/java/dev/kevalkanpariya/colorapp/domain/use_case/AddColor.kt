package dev.kevalkanpariya.colorapp.domain.use_case

import dev.kevalkanpariya.colorapp.domain.model.Color
import dev.kevalkanpariya.colorapp.domain.repository.ColorRepository

class AddColor(
    private val repository: ColorRepository
) {

    suspend operator fun invoke(note: Color){
        repository.insertColor(note)
    }
}