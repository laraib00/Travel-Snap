package com.example.splashscreencompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(navController: NavHostController)
{
    val animations=listOf(
        R.raw.intro1,
        R.raw.intro2,
        R.raw.intro3
    )
    val titles= listOf(
        "Explore the World",
        "Seaside Escapes",
        "Garden Gateways"
    )
    val description=listOf(
        "Find thousands of tourist destinations ready for you to visit.",
        "Embark on unforgettable journey to renowned destinations. ",
        "Experience the finest city and garden tours right at your fingertips with our app."
    )
    val pagerState= rememberPagerState (
pageCount = animations.size
    )
    Column (Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
HorizontalPager(state = pagerState) {

}
    }


}
