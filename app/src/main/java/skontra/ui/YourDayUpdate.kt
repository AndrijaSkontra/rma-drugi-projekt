package skontra.ui

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import skontra.data.YourDay
import skontra.domain.UpdateYourDayUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YourDayUpdate(
    setScreenState: (ScreenState) -> Unit,
    yourDay: YourDay?,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    updateYourDayUseCase: UpdateYourDayUseCase
) {
    val (productivityRating, setProductivityRating) =
        remember { mutableStateOf(yourDay?.productivity ?: 0) }
    val (stressRating, setStressRating) =
        remember { mutableStateOf(yourDay?.stress ?: 0) }
    val (leftComfortZone, setLeftComfortZone) =
        remember { mutableStateOf(yourDay?.leftComfortZone ?: false) }
    val (noteOfTheDay, setNoteOfTheDay) =
        remember { mutableStateOf(yourDay?.note ?: "") }
    val (selectedDate, setSelectedDate) =
        remember { mutableStateOf<Long?>(yourDay?.date ?: System.currentTimeMillis()) }
    val (starRating, setStarRating) =
        remember { mutableStateOf(yourDay?.overallDayRating ?: 0) }
    val (imageUri, setImageUri) =
        remember { mutableStateOf<Uri?>(yourDay?.pictureUrl) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .wrapContentSize(Alignment.Center)
            .verticalScroll(scrollState)
    ) {
        NumberPickSlider(
            selectedNumber = productivityRating,
            onNumberSelected = setProductivityRating,
            text = "Productivity"
        )

        NumberPickSlider(
            selectedNumber = stressRating,
            onNumberSelected = setStressRating,
            text = "Stress"
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CheckboxWithText(
                isChecked = leftComfortZone,
                setIsChecked = setLeftComfortZone,
                text = "Left Comfort Zone"
            )
            DateDialog(
                selectedDate = selectedDate,
                onDateSelected = setSelectedDate
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            TextField(
                value = noteOfTheDay,
                onValueChange = setNoteOfTheDay,
                label = { Text("Note for the day") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            StarRating(
                rating = starRating,
                onRatingChange = setStarRating
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CameraIcon { selectedImageUri ->
                setImageUri(selectedImageUri)
            }
        }

        if (imageUri != null) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Image of the day",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFF9C27B0), CircleShape)
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = { setScreenState(ScreenState.LIST_YOUR_DAYS) }) {
                Text(text = "Cancel")
            }
            FilledTonalButton(
                onClick = {
                    coroutineScope.launch {
                        var validInformation =
                            productivityRating != 0 && stressRating != 0 && selectedDate != null && starRating != 0
                        if (validInformation) {
                            val updatedYourDay = yourDay?.copy(
                                productivity = productivityRating,
                                stress = stressRating,
                                leftComfortZone = leftComfortZone,
                                note = noteOfTheDay,
                                overallDayRating = starRating,
                                pictureUrl = imageUri,
                                date = selectedDate ?: System.currentTimeMillis()
                            ) ?: YourDay(
                                productivity = productivityRating,
                                stress = stressRating,
                                leftComfortZone = leftComfortZone,
                                note = noteOfTheDay,
                                overallDayRating = starRating,
                                pictureUrl = imageUri,
                                date = selectedDate ?: System.currentTimeMillis()
                            )
                            updateYourDayUseCase.update(context, updatedYourDay)
                            setScreenState(ScreenState.LIST_YOUR_DAYS)
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar(message = "Productivity, Stress and Rating required.")
                            }
                        }
                    }
                },
            ) {
                Text("Update")
            }
        }
    }
}