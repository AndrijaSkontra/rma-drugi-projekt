package com.example.yourday

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YourDayAdd(
    setYourDay: (YourDay?) -> Unit,
    setScreenState: (ScreenState) -> Unit,
    yourDays: List<YourDay>
) {
    Text(text = "Add")
}
