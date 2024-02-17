package com.example.splashscreencompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val navController= rememberNavController()
                NavHost(navController = navController, startDestination = "Splash")
                {
                    composable("Splash")
                    {
                        SplashScreen(navController=navController, context= this@MainActivity)
                    }
                    composable("Onboarding")
                    {
                        OnboardingScreen(navController = navController,context=this@MainActivity)
                    }
                    composable("Login")
                    {
                        LoginScreen(navController = navController)
                    }

                    composable("Home")
                    {
                        Home(navController = navController)
                    }
                    composable("Signup")
                    {
                        SignupScreen(navController = navController)
                    }
                    composable("SignupBoardingScreen")
                    {
                        SignupBoardingScreen(navController = navController)
                    }



                }

            }
        }
    }
}
@Composable
fun Navigation()
{

}



