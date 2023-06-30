package dev.kevalkanpariya.colorapp.domain.use_case

import dev.kevalkanpariya.colorapp.domain.model.SyncColorId
import dev.kevalkanpariya.colorapp.domain.repository.ColorRepository

class AddSyncColorId(
    private val repository: ColorRepository
) {

    suspend operator fun invoke(id: SyncColorId){
        repository.insertSyncColorId(id)
    }
}