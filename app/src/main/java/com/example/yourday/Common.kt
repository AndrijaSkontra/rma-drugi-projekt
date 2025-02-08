package com.example.yourday

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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