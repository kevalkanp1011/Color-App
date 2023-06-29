package dev.kevalkanpariya.colorapp.presentation

import dev.kevalkanpariya.colorapp.domain.model.Color

data class ColorsState(
    val colors: List<Color> =emptyList(),
)
