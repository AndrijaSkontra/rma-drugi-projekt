package com.example.yourday

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YourDayAdd(
    setYourDay: (YourDay?) -> Unit,
    setScreenState: (ScreenState) -> Unit,
    setYourDays: (List<YourDay>) -> Unit,
) {
    val (productivityRating, setProductivityRating) = remember { mutableStateOf<Int>(0) }
    val (stressRating, setStressRating) = remember { mutableStateOf<Int>(0) }
    val (leftComfortZone, setLeftComfortZone) = remember { mutableStateOf<Boolean>(false) }
    val (noteOfTheDay, setNoteOfTheDay) = remember { mutableStateOf<String>("") }
    val (selectedDate, setSelectedDate) = remember { mutableStateOf<Long?>(null) }
    val (starRating, setStarRating) = remember { mutableStateOf(0) }
    val (imageUri, setImageUri) = remember { mutableStateOf<Uri?>(null) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
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
        Box(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            TextField(
                value = noteOfTheDay,
                onValueChange = { setNoteOfTheDay(it) },
                label = { Text("Note for the day") }, // Placeholder
                modifier = Modifier.fillMaxWidth()
            )

        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center
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
                println(selectedImageUri)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
        ) {
            OutlinedButton(onClick = {
                setScreenState(ScreenState.LIST_YOUR_DAYS)
            }) { Text(text = "Back") }
            Button(onClick = {
                println("Productivity Rating: $productivityRating")
                println("Stress Rating: $stressRating")
                println("Left Comfort Zone: $leftComfortZone")
                println("Note of the Day: $noteOfTheDay")
                println("Selected Date: ${selectedDate?.let { formatDate(it) } ?: "None"}")
                println("Star Rating: $starRating")
                println("Image URI: $imageUri")
                var validInformation =
                    productivityRating != 0 && stressRating != 0 && selectedDate != null && starRating != 0
                if (validInformation) {
                    setScreenState(ScreenState.LIST_YOUR_DAYS)
                } else {
                    println("Wrong data input")
                }
            }) { Text(text = "Add Day") }
        }

    }
}

