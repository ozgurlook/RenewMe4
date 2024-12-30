package com.renewme.renewme.addiction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddictionScreen(
    onAddictionSelected: (AddictionType) -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF097A09))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddictionButton("Smoking") {
                onAddictionSelected(AddictionType.SMOKING)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            AddictionButton("Alcohol") {
                onAddictionSelected(AddictionType.ALCOHOL)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            AddictionButton("Drug") {
                onAddictionSelected(AddictionType.DRUG)
            }
        }
    }
}

@Composable
private fun AddictionButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF097A09)
        )
    ) {
        Text(text = text)
    }
}

enum class AddictionType {
    SMOKING,
    ALCOHOL,
    DRUG
} 