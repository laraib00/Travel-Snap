package com.example.splashscreencompose.gesture

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.splashscreencompose.R
import com.example.splashscreencompose.utils.LocalFileProvider.fetchLocalImages
import com.example.splashscreencompose.utils.LocalImages
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlin.math.roundToInt

@Composable
fun GalleryScreen(navController: NavController) {

    val context = LocalContext.current
    val permissionState = rememberPermissionState()
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionState.value =
                if (isGranted) PermissionState.Granted else PermissionState.Denied
        }

    Log.d("permissionValue","started: ${permissionState.value}")

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (permissionState.value) {
            PermissionState.Granted -> {
                Log.d("permissionValue","Granted: ${permissionState.value}")
                val images = fetchLocalImages(context)
                if (images.isNotEmpty()) {
                    ImageGrid(images, navController)
                    Log.d("Uri", "url: ${images.first().url} Uri: ${Uri.parse(images.first().url)}")
                } else {
                    Text("No images found")
                }
            }

            PermissionState.Denied ->  {
                Log.d("permissionValue","Denied: ${permissionState.value}")
                // Text("Permission denied")
                PermissionRequestButton(permissionState)
            }
            else -> {
                Log.d("permissionValue","Else: ${permissionState.value}")
                LaunchedEffect(permissionState) {
                    requestPermissionLauncher.launch(permission)
                }
            } // PermissionRequestButton(permissionState)
        }
    }
}

@Composable
fun ImageGrid(images: List<LocalImages>, navController: NavController) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
        items(images) { photo ->
            GalleryImage(imageUri = Uri.parse(photo.url)) {
                navController.navigate(route = "imageDetail/${photo.id}")
            }
        }
    }
}


@Composable
fun GalleryImage(imageUri: Uri ,shape: Shape = RoundedCornerShape(8.dp), click: () -> Unit) {
    Image(
        painter = rememberImagePainter(imageUri),
        contentDescription = null,
        modifier = Modifier
            .padding(4.dp)
            .size(100.dp)
            .clip(shape)
            .clickable(onClick = click),
        contentScale = ContentScale.Crop  //
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewGalleryImage() {
    // GalleryImage(Uri.parse("https://fastly.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI"))
}

@Preview(showBackground = true)
@Composable
fun PreviewGalleryScreen() {
    // Dummy list of images for preview
    val images = listOf(
        LocalImages(
            1,
            "Image 1",
            "content://media/external/images/media/1",
            100F,
            "2022-01-01",
            "image/jpeg"
        ),
        LocalImages(
            2,
            "Image 2",
            "content://media/external/images/media/2",
            150F,
            "2022-01-02",
            "image/jpeg"
        ),
        LocalImages(
            3,
            "Image 3",
            "content://media/external/images/media/3",
            200F,
            "2022-01-03",
            "image/jpeg"
        )
    )
    // GalleryScreen()
}

enum class PermissionState {
    Granted,
    Denied,
    Unknown
}

@Composable
fun rememberPermissionState(): MutableState<PermissionState> {
    val context = LocalContext.current
    val permissionState = remember { mutableStateOf(PermissionState.Unknown) }
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    DisposableEffect(context) {
        val permissionStatus = ContextCompat.checkSelfPermission(
            context,
            permission
        )
        permissionState.value = if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            PermissionState.Granted
        } else {
            PermissionState.Unknown
        }

        onDispose { }
    }

    return permissionState
}

@Composable
fun MutableState<PermissionState>.requestPermission(context: Context) {
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            value = if (isGranted) PermissionState.Granted else PermissionState.Denied
        }
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    requestPermissionLauncher.launch(permission)
}

@Composable
fun FullScreenImage(url: Long) {
    Log.d("fHd", "FullScreenImage: $url")
    val imageList = fetchLocalImages(LocalContext.current)
    var uri: Uri?=null
    imageList.forEach {
        if (it.id  ==url){
            uri = Uri.parse(it.url)
        }
    }

    uri?.let { ImageWithZoom(it) }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageWithZoom(uri: Uri) {
    val density = LocalDensity.current

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Start,
            anchors = DraggableAnchors {
                DragAnchors.Start at 0f
                DragAnchors.End at 1000f
            },
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        )
    }


    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
    Image(
        painter = rememberImagePainter(uri),
        contentDescription = null,
        modifier = Modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    // Update the scale based on zoom gestures.
                    scale *= zoom

                    // Limit the zoom levels within a certain range (optional).
                    scale = scale.coerceIn(0.5f, 3f)

                    // Update the offset to implement panning when zoomed.
                    offset = if (scale == 1f) Offset(0f, 0f) else offset + pan
                }
            }
            .graphicsLayer(
                scaleX = scale, scaleY = scale,
                translationX = offset.x, translationY = offset.y
            )
            .offset {
                IntOffset(
                    x = state
                        .requireOffset()
                        .roundToInt(), y = 0
                )
            }
            .anchoredDraggable(state = state, orientation = Orientation.Horizontal)
    )
}


@Composable
fun PermissionRequestButton(permissionState: MutableState<PermissionState>) {
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionState.value =
                if (isGranted) PermissionState.Granted else PermissionState.Denied
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Permission is Required for images",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { requestPermissionLauncher.launch(permission) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF0FA3E2))
        ) {
            Text("Request Permission")
        }
    }
}

enum class DragAnchors {
    Start,
    End,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeAblePages() {
    val images = listOf(
        R.drawable.image,
        R.drawable.image2,
        R.drawable.image3
    )
    val pagerState = rememberPagerState { images.size}
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
       HorizontalPager(
           state = pagerState,
           key = { images[it]},
           pageSize = PageSize.Fill
           ) { index ->
           Image(
               painter = painterResource(images[index]),
               contentDescription = null,
               contentScale = ContentScale.Fit,
               modifier = Modifier
                   .fillMaxSize()
           )
       }
    }
}

