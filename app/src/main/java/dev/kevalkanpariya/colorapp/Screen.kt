package dev.kevalkanpariya.colorapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kevalkanpariya.colorapp.presentation.ColorViewModel
import dev.kevalkanpariya.colorapp.presentation.ColorsState
import dev.kevalkanpariya.colorapp.presentation.UiEvent
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(viewModel: ColorViewModel) {
    val state= viewModel.state.value
    val syncState = viewModel.cardSync.value
    val hostState= remember { SnackbarHostState() }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                viewModel, hostState
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = hostState) { snackbarData ->

            }
        }
    ) {

        Content(state, syncState)
    }
}



@Composable
fun Content(state: ColorsState, syncState: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TopBar(syncNo = syncState)
        LazyVerticalGrid(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 12.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(state.colors) {
                CardItem(color = it.color, date = it.timestamp)
            }


        }
    }
}

@Composable
fun CardItem(color: Int, date: String) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(color)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(7.dp)) {
            Text(
                text = "#${color.toString(16)}",
                fontSize = 18.sp,
                color = Color.White
            )
            Divider(
                modifier = Modifier.width(90.dp),
                thickness = 1.dp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(28.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End) {
                Text(
                    text = "Created at",
                    fontSize = 14.sp,
                    color = Color(0xFFFFFEFE),
                    textAlign = TextAlign.Right,
                )
                Text(
                    text = date,
                    fontSize = 14.sp,
                    color = Color(0xFFFFFEFE),
                    textAlign = TextAlign.Right,
                )
            }


        }
    }
}



@Composable
fun TopBar(syncNo: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0XFF5659A4))
            .padding(horizontal = 15.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Color App",
            fontSize = 24.sp,
            color = Color.White
        )
        Button(
            modifier = Modifier.width(70.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0XFFB6B9FF)
            ),
            contentPadding = PaddingValues(end = 5.dp, start = 13.dp, top = 5.dp, bottom = 5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$syncNo"
                )
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.baseline_sync_24),
                    contentDescription = null,
                    tint = Color(0XFF5659A4)
                )
            }

        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FloatingActionButton(viewModel: ColorViewModel, hostState: SnackbarHostState) {

    val scope = rememberCoroutineScope()
    Button(
        modifier = Modifier.width(123.dp),
        onClick = {
            scope.launch {
                viewModel.onEvent(UiEvent.AddColor)
                hostState.showSnackbar(
                    message = "Note Added",
                )
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0XFFB6B9FF)
        ),
        contentPadding = PaddingValues(end = 6.dp, start = 12.dp, top = 6.dp, bottom = 6.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Add Color",
                fontSize = 15.sp,
                color = Color(0XFF5659A4)
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_circle_24),
                contentDescription = null,
                tint = Color(0XFF5659A4),
                modifier = Modifier.size(30.dp)
            )
        }

    }
}


