package com.example.yourday

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.yourday.ui.theme.YourdayTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YourdayTheme {
                val yourDaysDefault = generateYourDayList(4)
                val (yourDays, setYourDays) = remember {
                    mutableStateOf<List<YourDay>>(
                        yourDaysDefault
                    )
                }
                val (yourDay, setYourDay) = remember { mutableStateOf<YourDay?>(null) }
                val (screenState, setScreenState) = remember {
                    mutableStateOf<ScreenState>(
                        ScreenState.LIST_YOUR_DAYS
                    )
                }

                if (screenState == ScreenState.LIST_YOUR_DAYS) {
                    YourDaysList(
                        setYourDay = setYourDay,
                        setScreenState = setScreenState,
                        yourDays = yourDays
                    )
                }

                if (screenState == ScreenState.DETAILS_YOUR_DAY) {
                    YourDayDetails(
                        setYourDay = setYourDay,
                        setScreenState = setScreenState,
                        yourDays = yourDays,
                        yourDay = yourDay
                    )
                }

                if (screenState == ScreenState.ADD_YOUR_DAY) {
                    YourDayAdd(setYourDay = setYourDay, setScreenState = setScreenState, setYourDays = setYourDays)
                }
            }
        }
    }
}

