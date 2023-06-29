package dev.kevalkanpariya.colorapp.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kevalkanpariya.colorapp.domain.use_case.ColorUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class ColorViewModel @Inject constructor(
    private val colorUseCases: ColorUseCases
): ViewModel() {


    private val _state= mutableStateOf(ColorsState())
    val state: State<ColorsState> = _state

    private val _cardSync = mutableStateOf(0)
    val cardSync: State<Int> = _cardSync

    private var getColorsJob: Job? = null

    init {
        getColors()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: UiEvent){
        when(event) {
            is UiEvent.AddColor -> {
                viewModelScope.launch {
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val currentDate = LocalDate.now().format(formatter)

                    val rnd = Random()
                    val color = android.graphics.Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                    val color2 = dev.kevalkanpariya.colorapp.domain.model.Color(
                        color = color,
                        timestamp = currentDate
                    )
                    colorUseCases.addColor(color2)
                    _cardSync.value++

                    getColors()
                }
            }

            is UiEvent.Sync -> {

            }
            is UiEvent.ShowSnackBar -> {

            }
        }

    }

    private fun  getColors(){
        getColorsJob?.cancel()
        getColorsJob = colorUseCases.getColors().onEach {colors->
            _state.value = state.value.copy(colors = colors)
        }.launchIn(viewModelScope)
    }
}