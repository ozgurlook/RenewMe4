package com.renewme.renewme.userdata

data class UserData(
    val addiction: Addiction? = null,
    val supportType: SupportType? = null,
    val age: Int? = null,
    val height: Int? = null,
    val weight: Int? = null
)

enum class Addiction {
    SMOKING,
    ALCOHOL,
    SUBSTANCE_ABUSE,
    OTHER
}

enum class SupportType {
    PROGRESS_TRACKING,
    MEDICATION_REMINDERS,
    PSYCHOLOGICAL_SUPPORT,
    HEALTHCARE_SERVICES
} 