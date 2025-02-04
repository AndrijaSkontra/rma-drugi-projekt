package com.example.yourday

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
fun CheckboxMinimalExample(isChecked: Boolean, setIsChecked: (Boolean) -> Unit, text: String) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.W100
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = { setIsChecked(!isChecked) }
        )
    }
}