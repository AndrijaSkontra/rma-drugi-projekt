package com.example.yourday

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YourDayDetails(
    setScreenState: (ScreenState) -> Unit,
    yourDay: YourDay?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(16.dp),
    ) {
        // Top row with Back and Update buttons


        yourDay?.let { day ->
            // Header title
            Text(
                text = "Your Day Details",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.padding(8.dp))

            // Display properties of YourDay
            Text(text = "ID: ${day.id}")
            Text(text = "Productivity: ${day.productivity}")
            Text(text = "Stress: ${day.stress}")
            if (day.note != "") {
                Text(text = "Note: ${day.note}")
            }

            // Format and display the date
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(Date(day.date))
            Text(text = "Date: $formattedDate")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(text = "Left Comfort Zone: ")
                if (day.leftComfortZone) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_check_circle_24),
                        contentDescription = "Checkbox",
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.outline_check_box_outline_blank_24),
                        contentDescription = "No CheckBox",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))

            // Display Overall Day Rating using stars
            Text(text = "Overall Day Rating:")
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Draw full stars
                for (i in 1..day.overallDayRating) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_star_24),
                        contentDescription = "Full Star",
                        modifier = Modifier.size(24.dp)
                    )
                }
                // Calculate and draw empty stars (assume maximum 10 stars)
                val maxStars = 10
                val emptyStarsAmount = maxStars - day.overallDayRating
                for (i in 1..emptyStarsAmount) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_star_border_24),
                        contentDescription = "Empty Star",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
            ) {
                OutlinedButton(onClick = { setScreenState(ScreenState.LIST_YOUR_DAYS) }) {
                    Text(text = "Back")
                }
                Button(onClick = { println("update todo") }) {
                    Text(text = "Update")
                }
            }
        } ?: run {
            // Fallback for null yourDay
            Text(text = "No data available")
        }
    }
}