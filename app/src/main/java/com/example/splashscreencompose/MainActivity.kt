package com.example.splashscreencompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.splashscreencompose.ui.theme.SplashScreenComposeTheme

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
        SplashScreen(navController=navController)
}
    composable("Onboarding")
    {
        OnboardingScreen(navController = navController)
    }
    composable("Home")
    {
        HomeScreen()
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



