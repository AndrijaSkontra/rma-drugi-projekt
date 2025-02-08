package com.example.yourday

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
    val (productivityRating, setProductivityRating) = remember { mutableStateOf<Int>(1) }
    val (stressRating, setStressRating) = remember { mutableStateOf<Int>(1) }
    val (leftComfortZone, setLeftComfortZone) = remember { mutableStateOf<Boolean>(false) }

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
        Row ( modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
            CheckboxWithText(
                isChecked = leftComfortZone,
                setIsChecked = setLeftComfortZone,
                text = "Left Comfort Zone"
            )
            DateDialog()
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
                setScreenState(ScreenState.LIST_YOUR_DAYS)
            }) { Text(text = "Add Day") }
        }

    }
}

