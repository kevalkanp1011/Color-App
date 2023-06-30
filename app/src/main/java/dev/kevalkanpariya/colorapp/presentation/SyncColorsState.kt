package dev.kevalkanpariya.colorapp.presentation

import dev.kevalkanpariya.colorapp.domain.model.Color

data class SyncColorsState(
    val colors: List<Color> = emptyList()
)
