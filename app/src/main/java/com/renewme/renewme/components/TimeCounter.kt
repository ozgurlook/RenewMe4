package com.renewme.renewme.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Duration
import kotlinx.coroutines.delay

@Composable
fun TimeCounter(startDate: LocalDate) {
    val currentTime = LocalDateTime.now()
    val startDateTime = startDate.atStartOfDay()
    
    val duration = Duration.between(startDateTime, currentTime)
    
    val years = duration.toDays() / 365
    val months = (duration.toDays() % 365) / 30
    val days = duration.toDays() % 30
    val hours = duration.toHours() % 24
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60
    
    var currentSeconds by remember { mutableStateOf(seconds) }
    
    LaunchedEffect(Unit) {
        while(true) {
            delay(1000)
            currentSeconds = Duration.between(startDateTime, LocalDateTime.now()).seconds % 60
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TimeUnit("Years", years)
            TimeUnit("Months", months)
            TimeUnit("Days", days)
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TimeUnit("Hours", hours)
            TimeUnit("Minutes", minutes)
            TimeUnit("Seconds", currentSeconds)
        }
    }
}

@Composable
private fun TimeUnit(label: String, value: Long) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
    }
} 