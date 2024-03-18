import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Home(navController: NavController) {

    var showDialog by remember { mutableStateOf(false) }

    BackHandler {
        // Intercept back button press
        showDialog = true
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen", fontSize = 44.sp)
    }

    if (showDialog) {
        ExitConfirmationDialog(
            onConfirmExit = {
                showDialog = false
                android.os.Process.killProcess(android.os.Process.myPid())
            },
            onCancel = { showDialog = false }
        )
    }
}

@Composable
fun ExitConfirmationDialog(
    onConfirmExit: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text("Exit Confirmation") },
        text = { Text("Are you sure you want to exit the app?") },
        confirmButton = {
            Button(
                onClick = { onConfirmExit() },
            ) {
                Text("Exit")
            }
        },
        dismissButton = {
            Button(
                onClick = { onCancel() },
            ) {
                Text("Cancel")
            }
        }
    )
}
