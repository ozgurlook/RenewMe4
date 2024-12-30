package com.renewme.renewme.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.renewme.renewme.R
import com.renewme.renewme.addiction.AddictionDetailsScreen
import com.renewme.renewme.addiction.AddictionScreen
import com.renewme.renewme.addiction.AddictionTracker
import com.renewme.renewme.components.TimeCounter
import com.renewme.renewme.navigation.BottomNavItem
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HomeScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.HOME) }
    var showAddictionScreen by remember { mutableStateOf(false) }
    var showAddictionDetails by remember { mutableStateOf(false) }
    var selectedTracker: AddictionTracker? by remember { mutableStateOf(null) }
    var addictionTrackers by remember { mutableStateOf(listOf<AddictionTracker>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF097A09))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.homelogo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .padding(0.dp)
                    .width(81.dp)
                    .height(81.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Calendar
            Text(
                text = "Today",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Calendar Days
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in -2..2) {
                    val date = LocalDate.now().plusDays(i.toLong())
                    val isSelected = date == selectedDate
                    
                    CalendarDay(
                        date = date,
                        isSelected = isSelected,
                        onClick = { selectedDate = date }
                    )
                }
            }

            // Selected date content
            if (selectedDate != null) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Selected: ${selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Addiction Trackers
            LazyColumn {
                items(addictionTrackers) { tracker ->
                    AddictionTrackerCard(
                        tracker = tracker,
                        onClick = {
                            selectedTracker = tracker
                            showAddictionDetails = true
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // Bottom Navigation with Add Button
        NavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            BottomNavItem.values().forEach { item ->
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.title) },
                    label = { Text(item.title) },
                    selected = selectedNavItem == item,
                    onClick = { selectedNavItem = item }
                )
            }
            
            NavigationBarItem(
                icon = { Icon(Icons.Default.Add, "Add Addiction Counter") },
                label = { Text("Add") },
                selected = false,
                onClick = { showAddictionScreen = true }
            )
        }
    }

    if (showAddictionScreen) {
        AddictionScreen(
            onAddictionSelected = { addiction ->
                val newTracker = AddictionTracker(
                    type = addiction,
                    startDate = LocalDate.now()
                )
                addictionTrackers = addictionTrackers + newTracker
                showAddictionScreen = false
            },
            onDismiss = { showAddictionScreen = false }
        )
    }

    selectedTracker?.let { tracker ->
        if (showAddictionDetails) {
            AddictionDetailsScreen(
                tracker = tracker,
                onDismiss = { showAddictionDetails = false },
                onUpdate = { updatedTracker ->
                    addictionTrackers = addictionTrackers.map { 
                        if (it.type == updatedTracker.type) updatedTracker else it 
                    }
                    showAddictionDetails = false
                },
                onReset = {
                    addictionTrackers = addictionTrackers.map { 
                        if (it.type == tracker.type) {
                            it.copy(startDate = LocalDate.now())
                        } else it 
                    }
                    showAddictionDetails = false
                }
            )
        }
    }
}

@Composable
fun CalendarDay(
    date: LocalDate,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .background(
                if (isSelected) Color.White else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .padding(12.dp)
    ) {
        Text(
            text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            color = if (isSelected) Color(0xFF006400) else Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = date.dayOfMonth.toString(),
            color = if (isSelected) Color(0xFF006400) else Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

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
        Text(
            text = "Clean for:",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
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
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun AddictionTrackerCard(
    tracker: AddictionTracker,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = tracker.type.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF097A09)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            TimeCounter(tracker.startDate)
            
            if (tracker.moneySaved > 0) {
                Text(
                    text = "Money saved: $${tracker.moneySaved}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF097A09)
                )
            }
        }
    }
} 