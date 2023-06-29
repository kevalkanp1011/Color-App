package dev.kevalkanpariya.colorapp.presentation

import dev.kevalkanpariya.colorapp.domain.model.Color

sealed class UiEvent {
    object AddColor: UiEvent()
    object Sync: UiEvent()
    data class ShowSnackBar(val message: String) : UiEvent()
}
