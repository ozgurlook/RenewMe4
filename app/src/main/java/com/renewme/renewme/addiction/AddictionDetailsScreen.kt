package com.renewme.renewme.addiction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import com.renewme.renewme.components.TimeCounter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddictionDetailsScreen(
    tracker: AddictionTracker,
    onDismiss: () -> Unit,
    onUpdate: (AddictionTracker) -> Unit,
    onReset: () -> Unit
) {
    var notes by remember { mutableStateOf(tracker.notes) }
    var dailyCost by remember { mutableStateOf(tracker.dailyCost.toString()) }
    var newTrigger by remember { mutableStateOf("") }
    var triggers by remember { mutableStateOf(tracker.triggers) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("${tracker.type.name} Details") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TimeCounter(tracker.startDate)
                    
                    Button(
                        onClick = onReset,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Reset Counter")
                    }
                }
                
                if (tracker.dailyCost > 0) {
                    val savedMoney = tracker.dailyCost * 
                        ChronoUnit.DAYS.between(tracker.startDate, LocalDate.now())
                    
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Money Saved",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "$${String.format("%.2f", savedMoney)}",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = dailyCost,
                    onValueChange = { dailyCost = it },
                    label = { Text("Daily Cost ($)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Triggers:")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newTrigger,
                        onValueChange = { newTrigger = it },
                        label = { Text("Add Trigger") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        if (newTrigger.isNotEmpty()) {
                            triggers = triggers + newTrigger
                            newTrigger = ""
                        }
                    }) {
                        Icon(Icons.Default.Add, "Add Trigger")
                    }
                }
                
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(triggers) { trigger ->
                        AssistChip(
                            onClick = { triggers = triggers - trigger },
                            label = { Text(trigger) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onUpdate(
                    tracker.copy(
                        notes = notes,
                        dailyCost = dailyCost.toDoubleOrNull() ?: 0.0,
                        triggers = triggers,
                        moneySaved = (dailyCost.toDoubleOrNull() ?: 0.0) * 
                            ChronoUnit.DAYS.between(tracker.startDate, LocalDate.now())
                    )
                )
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 