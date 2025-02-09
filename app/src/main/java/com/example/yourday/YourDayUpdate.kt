package com.example.yourday

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YourDayUpdate(
    setScreenState: (ScreenState) -> Unit,
    yourDay: YourDay?
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .wrapContentSize(Alignment.Center)
    ) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        Text("updateScreen")
        OutlinedButton(onClick = { setScreenState(ScreenState.LIST_YOUR_DAYS) }) {
            Text(text = "Cancel")
        }
        FilledTonalButton(
            onClick = {
                coroutineScope.launch {
                    val database = YourDayDatabase.getDatabase(context)
                    if (yourDay != null) {
                        database.yourDayDao().update(yourDay)
                    }
                    setScreenState(ScreenState.LIST_YOUR_DAYS)
                }
            },
        ) {
            Text("Update")
        }
    }

}
