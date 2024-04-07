package com.example.splashscreencompose

import Home
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.splashscreencompose.gesture.FullScreenImage
import com.example.splashscreencompose.gesture.GalleryScreen
import com.example.splashscreencompose.gesture.SwipeAblePages

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentDestination = intent.getStringExtra("destination")
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = getStartDestination(intentDestination))
                {
                    composable("Splash")
                    {
                        SplashScreen(navController = navController, context = this@MainActivity)
                    }
                    composable("Onboarding")
                    {
                        OnboardingScreen(navController = navController, context = this@MainActivity)
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
                    composable("ForgetPassword")
                    {
                        ForgetPassword(navController = navController)
                    }
                    composable("CreatePassword")
                    {
                        CreatePassword(navController = navController)
                    }

                    activity("homeActivity") {
                        Intent(this@MainActivity, HomeActivity::class.java)
                    }

                    composable("gallery") {
                        GalleryScreen(navController)
                    }

                    composable(
                        route = "imageDetail/{imageUrl}",
                        arguments = listOf(navArgument("imageUrl") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val imageUrl = backStackEntry.arguments?.getLong("imageUrl")
                            ?: 0L
                        FullScreenImage(imageUrl)
                    }

                    composable("swipeAblePages") {
                        SwipeAblePages()
                    }
                }

            }
        }
    }
}

private fun getStartDestination(intentDestination: String?): String {
    return if (intentDestination != null && intentDestination == "gallery") {
        "gallery"
    } else {
        "splash"
    }
}



