package com.renewme.renewme.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.renewme.renewme.R
import com.renewme.renewme.navigation.BottomNavItem
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.HOME) }

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
        }

        // Bottom Navigation
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