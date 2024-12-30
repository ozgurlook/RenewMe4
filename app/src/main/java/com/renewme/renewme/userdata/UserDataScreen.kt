package com.renewme.renewme.userdata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.text.style.TextAlign

val CustomPurple = Color(0xFFA5A5F2)

@Composable
fun UserDataScreen(
    onComplete: (UserData) -> Unit
) {
    var currentStep by remember { mutableStateOf(0) }
    var selectedAddiction by remember { mutableStateOf<Addiction?>(null) }
    var selectedSupportType by remember { mutableStateOf<SupportType?>(null) }
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (currentStep) {
                0 -> {
                    QuestionSection(
                        question = "Which addiction are you struggling with?",
                        options = Addiction.values().map { it.name.replace("_", " ") },
                        selectedIndex = selectedAddiction?.ordinal,
                        onOptionSelected = { index ->
                            selectedAddiction = Addiction.values()[index]
                        }
                    )
                }
                1 -> {
                    QuestionSection(
                        question = "What type of support do you need?",
                        options = SupportType.values().map { 
                            when (it) {
                                SupportType.PROGRESS_TRACKING -> "Addiction progress tracking"
                                SupportType.MEDICATION_REMINDERS -> "Medication reminders"
                                SupportType.PSYCHOLOGICAL_SUPPORT -> "Psychological support"
                                SupportType.HEALTHCARE_SERVICES -> "Seeking healthcare services"
                            }
                        },
                        selectedIndex = selectedSupportType?.ordinal,
                        onOptionSelected = { index ->
                            selectedSupportType = SupportType.values()[index]
                        }
                    )
                }
                2 -> {
                    PersonalInfoSection(
                        age = age,
                        height = height,
                        weight = weight,
                        onAgeChange = { age = it },
                        onHeightChange = { height = it },
                        onWeightChange = { weight = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (currentStep < 2) {
                        currentStep++
                    } else {
                        onComplete(
                            UserData(
                                addiction = selectedAddiction,
                                supportType = selectedSupportType,
                                age = age.toIntOrNull(),
                                height = height.toIntOrNull(),
                                weight = weight.toIntOrNull()
                            )
                        )
                    }
                },
                enabled = when (currentStep) {
                    0 -> selectedAddiction != null
                    1 -> selectedSupportType != null
                    2 -> age.isNotEmpty() && height.isNotEmpty() && weight.isNotEmpty()
                    else -> false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomPurple,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(
                    text = if (currentStep < 2) "Next" else "Complete",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun QuestionSection(
    question: String,
    options: List<String>,
    selectedIndex: Int?,
    onOptionSelected: (Int) -> Unit
) {
    Text(
        text = question,
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    
    options.forEachIndexed { index, option ->
        OutlinedButton(
            onClick = { onOptionSelected(index) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (selectedIndex == index) CustomPurple else Color.Transparent,
                contentColor = if (selectedIndex == index) Color.White else CustomPurple
            ),
            border = BorderStroke(
                width = 1.dp,
                color = CustomPurple
            )
        ) {
            Text(
                text = option,
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PersonalInfoSection(
    age: String,
    height: String,
    weight: String,
    onAgeChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onWeightChange: (String) -> Unit
) {
    Text(
        text = "Personal Information",
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    
    OutlinedTextField(
        value = age,
        onValueChange = { if (it.length <= 3) onAgeChange(it.filter { char -> char.isDigit() }) },
        label = { Text("Age") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedLabelColor = CustomPurple,
            unfocusedLabelColor = CustomPurple,
            focusedBorderColor = CustomPurple,
            unfocusedBorderColor = CustomPurple,
            cursorColor = CustomPurple
        ),
        modifier = Modifier.fillMaxWidth()
    )
    
    Spacer(modifier = Modifier.height(8.dp))
    
    OutlinedTextField(
        value = height,
        onValueChange = { if (it.length <= 3) onHeightChange(it.filter { char -> char.isDigit() }) },
        label = { Text("Height (cm)") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedLabelColor = CustomPurple,
            unfocusedLabelColor = CustomPurple,
            focusedBorderColor = CustomPurple,
            unfocusedBorderColor = CustomPurple,
            cursorColor = CustomPurple
        ),
        modifier = Modifier.fillMaxWidth()
    )
    
    Spacer(modifier = Modifier.height(8.dp))
    
    OutlinedTextField(
        value = weight,
        onValueChange = { if (it.length <= 3) onWeightChange(it.filter { char -> char.isDigit() }) },
        label = { Text("Weight (kg)") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedLabelColor = CustomPurple,
            unfocusedLabelColor = CustomPurple,
            focusedBorderColor = CustomPurple,
            unfocusedBorderColor = CustomPurple,
            cursorColor = CustomPurple
        ),
        modifier = Modifier.fillMaxWidth()
    )
} 