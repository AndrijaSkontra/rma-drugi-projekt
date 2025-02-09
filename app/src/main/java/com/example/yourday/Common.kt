package com.example.yourday

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun NumberPickSlider(
    selectedNumber: Int,
    onNumberSelected: (Int) -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = text,
            fontWeight = FontWeight.W100
        )
        Spacer(modifier = Modifier.height(8.dp))
        Slider(
            value = selectedNumber.toFloat(),
            onValueChange = { newValue ->
                onNumberSelected(newValue.roundToInt())
            },
            valueRange = 1f..5f,
            steps = 3
        )
    }
}

@Composable
fun CheckboxWithText(isChecked: Boolean, setIsChecked: (Boolean) -> Unit, text: String) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { setIsChecked(!isChecked) }
        )
        Text(
            text = text,
            fontWeight = FontWeight.W100
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun DateDialog() {
    val (showDialog, setShowDialog) = remember { mutableStateOf<Boolean>(false) }
    val (selectedDate, setSelectedDate) = remember { mutableStateOf<Long?>(null) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { setShowDialog(true) }) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Open Date Picker",
                modifier = Modifier.size(30.dp)
            )
        }
        Text("${selectedDate?.let { formatDate(it) } ?: ""}", fontWeight = FontWeight.W100)

        if (showDialog) {
            DatePickerModal(
                onDateSelected = { date ->
                    setSelectedDate(date)
                    setShowDialog(false)
                },
                onDismiss = { setShowDialog(false) }  // Close dialog when dismissed
            )
        }
    }
}

fun formatDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val calendar = Calendar.getInstance().apply { timeInMillis = millis }
    return formatter.format(calendar.time)
}

@Composable
fun NumberInputField() {
    var text by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                val newValue = it.filter { char -> char.isDigit() } // Only allow numbers
                val intValue = newValue.toIntOrNull()

                if (intValue == null || intValue !in 1..10) {
                    error = true
                } else {
                    error = false
                }
                text = newValue
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Enter a number (1-10)") },
            isError = error,
            modifier = Modifier.fillMaxWidth()
        )

        if (error) {
            Text("Please enter a number between 1 and 10", color = Color.Red)
        }
    }
}


@Composable
fun StarRating(
    modifier: Modifier = Modifier,
    totalStars: Int = 10,
    initialRating: Int = 0
) {
    // Holds the current rating value
    var rating by remember { mutableStateOf(initialRating) }

    Row(modifier = modifier) {
        // Loop through each star position (1 through totalStars)
        for (i in 1..totalStars) {
            // Determine the star image based on whether its position is <= rating
            val starIcon = if (i <= rating) {
                R.drawable.baseline_star_24       // Replace with your "filled" star drawable
            } else {
                R.drawable.baseline_star_border_24 // Replace with your "empty" star drawable
            }

            Image(
                painter = painterResource(id = starIcon),
                contentDescription = if (i <= rating) "Filled Star" else "Empty Star",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { rating = i } // When clicked, set the rating to the star's position
            )
        }
    }
}

@Composable
fun CameraIcon(
    modifier: Modifier = Modifier,
    onImagePicked: (Uri?) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImagePicked(uri)
    }


    Row ( modifier = Modifier.clickable { launcher.launch("image/*") }) {
        Text(
            "Picture of the day", fontWeight = FontWeight.W100,
            modifier = Modifier.padding(end = 8.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.sharp_camera_alt_24),
            contentDescription = "Camera"
        )
    }
}