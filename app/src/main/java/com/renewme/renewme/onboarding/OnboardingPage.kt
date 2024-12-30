package com.renewme.renewme.onboarding

data class OnboardingPage(
    val title: String,
    val description: String? = null
)

val onboardingPages = listOf(
    OnboardingPage(
        title = "Your journey to renewal starts here."
    ),
    OnboardingPage(
        title = "Stay Motivated",
        description = "Receive reminders and encouragement to keep going"
    ),
    OnboardingPage(
        title = "Build Healthy Habits",
        description = "Create lasting changes with personalized habit tracking"
    ),
    OnboardingPage(
        title = "Connect With Support",
        description = "Join a community of like-minded individuals and mentors"
    ),
    OnboardingPage(
        title = "Emergency Support",
        description = "Access instant support in moments of challenge"
    )
) 