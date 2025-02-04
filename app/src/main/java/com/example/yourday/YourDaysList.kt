package com.example.yourday

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YourDaysList(
    setYourDay: (YourDay?) -> Unit,
    setScreenState: (ScreenState) -> Unit,
    yourDays: List<YourDay>
) {
    val ALL_STARS_AMOUNT = 10
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .background(color = Color.Black)
            .statusBarsPadding()
    ) {
        Text("Your Days", fontWeight = FontWeight.Bold, fontSize = 30.sp, color = Color.LightGray)
        yourDays.forEach { yourDay ->
            val emptyStarsAmount = ALL_STARS_AMOUNT - yourDay.overallDayRating
            Card(
                modifier = Modifier
                    .widthIn(400.dp, 600.dp)
                    .height(100.dp)
                    .padding(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
                onClick = {
                    setYourDay(yourDay)
                    setScreenState(ScreenState.DETAILS_YOUR_DAY)
                }
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        yourDay.date.toString(),
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray
                    )
                    HorizontalDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.CenterStart)
                    ) {
                        for (i in 1..yourDay.overallDayRating) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_star_24),
                                contentDescription = "Full Star"
                            )
                        }
                        for (i in 1..emptyStarsAmount) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_star_border_24),
                                contentDescription = "Empty Star"
                            )
                        }
                    }
                }
            }
        }
    }
}
