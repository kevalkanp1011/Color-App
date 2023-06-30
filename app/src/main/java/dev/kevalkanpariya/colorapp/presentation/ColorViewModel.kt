package dev.kevalkanpariya.colorapp.presentation

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kevalkanpariya.colorapp.domain.model.Color
import dev.kevalkanpariya.colorapp.domain.model.SyncColorId
import dev.kevalkanpariya.colorapp.domain.use_case.ColorUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class ColorViewModel @Inject constructor(
    private val colorUseCases: ColorUseCases,
    private val sharedPreferences: SharedPreferences
): ViewModel() {


    val db = FirebaseFirestore.getInstance()

    private val _state= mutableStateOf(ColorsState())
    val state: State<ColorsState> = _state

    private val _syncIdState= mutableStateOf(SyncColorIdState())
    val syncIdState: State<SyncColorIdState> = _syncIdState

    private val _syncColorsState= mutableStateOf(SyncColorsState())
    val syncColorsState: State<SyncColorsState> = _syncColorsState

    private val no = sharedPreferences.getInt("sync_no",0)

    private val _cardSync = mutableStateOf(no)
    val cardSync: State<Int> = _cardSync

    private var getColorsJob: Job? = null
    private var getSyncColorIdsJob: Job? = null
    private var getSyncColorsJob: Job? = null





    init {
        viewModelScope.launch {
            getColors()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun onEvent(event: UiEvent){
        when(event) {
            is UiEvent.AddColor -> {
                viewModelScope.launch {
                    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                    val currentDate = LocalDate.now().format(formatter)

                    val rnd = Random()
                    val color = android.graphics.Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                    val color2 = Color(
                        color = color,
                        timestamp = currentDate,
                        isInSync = true
                    )
                    _cardSync.value++
                    colorUseCases.addColor(color2)
                    sharedPreferences.edit()
                        .putInt("sync_no", _cardSync.value)
                        .apply()

                    getColors()
                }
            }

            is UiEvent.Sync -> {

                Log.d("VIEWMODEL Get Sync COLORS CALLED", "in event.sync${getColorsJob != null}")
                withContext(Dispatchers.IO) {
                    Log.d("VIEWMODEL Get Sync COLORS CALLED", "after IO${getColorsJob != null}")
                    getSyncColors()
                    Log.d("VIEWMODEL Get Sync COLORS CALLED", "after get colors${getColorsJob != null}")

                }

            }
            is UiEvent.ShowSnackBar -> {

            }
        }

    }

    private suspend fun  getColors(){
        getColorsJob?.cancel()
        getColorsJob = colorUseCases.getColors().onEach {colors->
            _state.value = state.value.copy(colors = colors)
        }.launchIn(viewModelScope)
    }


    private suspend fun getSyncColors() {
        getSyncColorsJob?.cancel()
        Log.d("VIEWMODEL Get Sync COLORS CALLED", "${getColorsJob != null}")
        getSyncColorsJob = viewModelScope.launch {
            colorUseCases.getSyncColors(isInSync = true).collect { colors ->
                Log.d("COLOR VIEWMODEL", "$colors")
                val batch = db.batch()
                val color_new = mutableListOf<Color>()
                for (color in colors) {
                    //Log.d("COLOR VIEWMODEL", "$color")
                    color_new.add(color)
                    val data = hashMapOf(
                        "color" to "${color.color}",
                        "timestamp" to color.timestamp
                    )

                    Log.d("COLOR INside", "$color")

                    val documentRef = db.collection("colors").document()
                    batch.set(documentRef, data)


                }
                batch.commit()
                    .addOnSuccessListener { documentReference ->
                        // Data added successfully
                        Log.d("SYNC COLOR 3RD", "sucess")

                        viewModelScope.launch {
                            color_new.forEach { color ->
                                colorUseCases.addColor(
                                    Color(
                                        id = color.id,
                                        timestamp = color.timestamp,
                                        color = color.color,
                                        isInSync = false
                                    )
                                )
                                _cardSync.value--
                                sharedPreferences.edit()
                                    .putInt("sync_no", _cardSync.value)
                                    .apply()
                                getSyncColorsJob?.cancel()
                            }



                        }
                    }
                    .addOnFailureListener { e ->
                        // Handle any errors
                    }
            }
        }
    }




}

const val TAG = "VIEWMODEL"

