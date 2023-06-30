package dev.kevalkanpariya.colorapp.domain.use_case

class ColorUseCases(
    val addColor: AddColor,
    val getColors: GetColors,
    val getColorsForSync: GetColorsForSync,
    val addSyncColorId: AddSyncColorId,
    val getSyncColorId: GetSyncColorId,
    val getSyncColors: GetSyncColors
)