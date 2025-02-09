package com.example.yourday

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.yourday.ui.theme.YourdayTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YourdayTheme {
                val context = LocalContext.current
                var yourDays by remember { mutableStateOf<List<YourDay>>(emptyList()) }
                var yourDay by remember { mutableStateOf<YourDay?>(null) }
                var screenState by remember { mutableStateOf(ScreenState.LIST_YOUR_DAYS) }

                LaunchedEffect(screenState) {
                    if (screenState == ScreenState.LIST_YOUR_DAYS) {
                        yourDays = generateYourDayList(context)
                    }
                }

                when (screenState) {
                    ScreenState.LIST_YOUR_DAYS -> {
                        YourDaysList(
                            setYourDay = { yourDay = it },
                            setScreenState = { screenState = it },
                            yourDays = yourDays
                        )
                    }
                    ScreenState.DETAILS_YOUR_DAY -> {
                        YourDayDetails(
                            setScreenState = { screenState = it },
                            yourDay = yourDay
                        )
                    }
                    ScreenState.ADD_YOUR_DAY -> {
                        YourDayAdd(
                            setYourDay = { yourDay = it },
                            setScreenState = { screenState = it },
                            setYourDays = { yourDays = it }
                        )
                    }

                    ScreenState.UPDATE_YOUR_DAY -> {
                        YourDayUpdate(
                            setScreenState = { screenState = it },
                            yourDay = yourDay
                        )
                    }
                }
            }
        }
    }
}