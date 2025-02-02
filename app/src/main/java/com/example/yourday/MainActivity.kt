package com.example.yourday

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourday.ui.theme.YourdayTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YourdayTheme {
                MainScreen()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val yourDays = generateYourDayList(8)
    println(yourDays)
    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)
        .statusBarsPadding()) {
        Text("Your Days", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        yourDays.forEach { yourDay ->
            Card(
                modifier = Modifier
                    .widthIn(400.dp, 600.dp)
                    .height(100.dp)
                    .padding(10.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        yourDay.date.toString(), fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
