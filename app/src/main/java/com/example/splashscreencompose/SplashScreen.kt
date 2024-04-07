@file:Suppress("DEPRECATION")

package com.example.splashscreencompose

import android.content.Context
import android.preference.PreferenceManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.splashscreencompose.utils.Constants.PREF_FIRST_TIME_OPENING_ONBOARDING
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, context: MainActivity)
{
    val alpha = remember{
        Animatable(0f)
    }
    LaunchedEffect(key1 = true)
    {
        alpha.animateTo(1f,
            animationSpec = tween(4000)
        )
        delay(5000)

        if (isFirstTimeOpening(context)) {
            // Open OnBoarding screens if it's the first time opening
            navController.navigate("Onboarding")
            setFirstTimeOpening(context, false)
        } else {
            // Proceed without opening OnBoarding
            navController.navigate("Login")
        }

        /*if (onBoardingIsFinished(context = context)) {
            navController.popBackStack()
            navController.navigate("Home")
        } else {
            navController.popBackStack()
            navController.navigate("Onboarding")

        }
        navController.popBackStack()
        navController.navigate("Onboarding")*/
    }
    Column (modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        LoaderAnimation(
            modifier=Modifier.size(400.dp),
            anim=R.raw.splash
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "Travel Snap",
            modifier = Modifier.alpha(alpha.value),
            fontSize = 52.sp,
            fontWeight = FontWeight.Bold,
        )


    }
}

@Composable
fun LoaderAnimation(modifier: Modifier, anim: Int)
{
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(anim))

    LottieAnimation(composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier)
}

private fun onBoardingIsFinished(context: MainActivity): Boolean {
    val sharedPreferences = context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isFinished", false)

}

fun isFirstTimeOpening(context: Context): Boolean {
    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
        PREF_FIRST_TIME_OPENING_ONBOARDING, true)
}

fun setFirstTimeOpening(context: Context, isFirstTimeOpening: Boolean) {
    PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(
        PREF_FIRST_TIME_OPENING_ONBOARDING, isFirstTimeOpening).apply()
}