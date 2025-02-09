package com.example.yourday

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.yourday.ui.theme.YourdayTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("RememberReturnType")
    @OptIn(ExperimentalMaterial3Api::class)
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
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(screenState) {
                    if (screenState == ScreenState.LIST_YOUR_DAYS) {
                        yourDays = generateYourDayList(context)
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Your Days", fontWeight = FontWeight.W200) }
                        )
                    },
                    snackbarHost = {
                        // Here we override the default content of the SnackbarHost.
                        SnackbarHost(hostState = snackbarHostState) { data ->
                            Snackbar(
                                snackbarData = data,
                                contentColor = Color.White,
                                containerColor = Color.Red
                            )
                        }
                    }

                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        when (screenState) {
                            ScreenState.LIST_YOUR_DAYS -> {
                                YourDaysList(
                                    setYourDay = { yourDay = it },
                                    setScreenState = { screenState = it },
                                    yourDays = yourDays,
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
                                    setYourDays = { yourDays = it },
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                )
                            }
                            ScreenState.UPDATE_YOUR_DAY -> {
                                YourDayUpdate(
                                    setScreenState = { screenState = it },
                                    yourDay = yourDay,
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}