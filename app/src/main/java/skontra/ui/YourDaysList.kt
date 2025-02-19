package skontra.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.yourday.R
import skontra.data.YourDay

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
            .fillMaxWidth()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .wrapContentSize(Alignment.Center)
            .padding(bottom = 70.dp)
    ) {
        yourDays.forEach { yourDay ->
            val emptyStarsAmount = ALL_STARS_AMOUNT - yourDay.overallDayRating
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .widthIn(400.dp, 600.dp)
                    .height(100.dp)
                    .padding(10.dp),
                onClick = {
                    setYourDay(yourDay)
                    setScreenState(ScreenState.DETAILS_YOUR_DAY)
                }
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        formatDate(yourDay.date),
                        fontWeight = FontWeight.W200,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        ) {
            Button(onClick = {
                setScreenState(ScreenState.ADD_YOUR_DAY)
            }) { Text(text = "Your Day +") }
        }
    }
}
