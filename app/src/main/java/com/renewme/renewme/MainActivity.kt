package com.renewme.renewme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.renewme.renewme.ui.theme.RenewMeTheme
import com.renewme.renewme.onboarding.OnboardingScreen
import com.renewme.renewme.userdata.UserDataScreen
import com.renewme.renewme.userdata.UserData
import com.renewme.renewme.login.LoginScreen
import com.renewme.renewme.auth.AuthViewModel
import com.renewme.renewme.register.RegisterScreen

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
                                // TODO: Ana ekrana git
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
                                // TODO: Ana ekrana git
                            },
                            onBackToLogin = {
                                showRegister = false
                                showLogin = true
                            }
                        )
                    }
                    else -> {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Greeting(
                                name = "Android",
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RenewMeTheme {
        Greeting("Android")
    }
}