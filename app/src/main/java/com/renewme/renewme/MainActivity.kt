package com.renewme.renewme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.renewme.renewme.ui.theme.RenewMeTheme
import com.renewme.renewme.onboarding.OnboardingScreen
import com.renewme.renewme.userdata.UserDataScreen
import com.renewme.renewme.login.LoginScreen
import com.renewme.renewme.auth.AuthViewModel
import com.renewme.renewme.register.RegisterScreen
import com.renewme.renewme.home.HomeScreen

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        splashScreen.setKeepOnScreenCondition { true }
        
        android.os.Handler(mainLooper).postDelayed({
            splashScreen.setKeepOnScreenCondition { false }
        }, 2000)

        enableEdgeToEdge()
        setContent {
            RenewMeTheme {
                var showLogin by remember { mutableStateOf(false) }
                var showOnboarding by remember { mutableStateOf(true) }
                var showUserData by remember { mutableStateOf(false) }
                var showRegister by remember { mutableStateOf(false) }
                var showHome by remember { mutableStateOf(false) }
                
                when {
                    showOnboarding -> {
                        OnboardingScreen(
                            onFinish = {
                                showOnboarding = false
                                showUserData = true
                            }
                        )
                    }
                    showUserData -> {
                        UserDataScreen(
                            onComplete = { userData ->
                                showUserData = false
                                showLogin = true
                            }
                        )
                    }
                    showLogin -> {
                        LoginScreen(
                            viewModel = authViewModel,
                            onLoginSuccess = {
                                showLogin = false
                                showHome = true
                            },
                            onRegisterClick = {
                                showLogin = false
                                showRegister = true
                            }
                        )
                    }
                    showRegister -> {
                        RegisterScreen(
                            viewModel = authViewModel,
                            onRegisterSuccess = {
                                showRegister = false
                                showHome = true
                            },
                            onBackToLogin = {
                                showRegister = false
                                showLogin = true
                            }
                        )
                    }
                    showHome -> {
                        HomeScreen()
                    }
                    else -> {
                        HomeScreen()
                    }
                }
            }
        }
    }
}