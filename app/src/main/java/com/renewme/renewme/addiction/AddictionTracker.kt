package com.renewme.renewme.addiction

import java.time.LocalDate

data class AddictionTracker(
    val type: AddictionType,
    val startDate: LocalDate,
    val notes: String = "",
    val triggers: List<String> = emptyList(),
    val moneySaved: Double = 0.0,
    val dailyCost: Double = 0.0
) 