package com.example.splashscreencompose

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SignupScreen(navController: NavController) {
    // Firebase Authentication instance
    val auth = FirebaseAuth.getInstance()
    // Firestore instance
    val db = FirebaseFirestore.getInstance()

    val context = LocalContext.current

    // State for user input fields
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }

    // Handle user sign-up
    fun signUp() {
        isLoading = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User signed up successfully, store additional details in FireStore
                    val user = auth.currentUser
                    val userDetails = hashMapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "email" to email
                    )

                    user?.uid?.let { userId ->
                        db.collection("users").document(userId)
                            .set(userDetails)
                            .addOnSuccessListener {
                                isLoading = false
                                Log.d("status", "userId: $userId Success: $it")
                                // Navigate to next screen after successful sign-up
                                navController.navigate("SignupBoardingScreen")
                            }
                            .addOnFailureListener { e ->
                                isLoading = false
                                // Handle failure to store user details
                                Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
                                Log.d("status", "Failure: $e")
                            }
                    }
                } else {
                    // Handle sign-up failures
                    isLoading = false
                    Toast.makeText(context, task.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("status", "else Log: $task")
                }
            }
    }

    // Enable the button only when all fields are filled
    var isButtonEnabled by remember {
        mutableStateOf(false)
    }

    // Validation checks for all input fields
    // Enable the button only when all fields are filled
    if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
        isButtonEnabled = true
    } else {
        isButtonEnabled = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "pic",
            Modifier
                .padding(10.dp)
                .size(100.dp),
            contentScale = ContentScale.FillWidth,
        )
        Text(
            text = "Create an account",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { /* Handle password visibility toggle */ }) {
                    // Password visibility icon
                }
            },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { signUp() },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF0FA3E2)),
            enabled = isButtonEnabled // Enable button only when all fields are filled
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Sign Up", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}
