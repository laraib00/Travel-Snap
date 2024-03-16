package com.example.splashscreencompose

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.core.content.edit
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


private const val PREFS_NAME = "LoginPrefs"
private const val PREF_EMAIL = "email"
private const val PREF_PASSWORD = "password"
private const val PREF_REMEMBER_ME = "rememberMe"

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var passwordVisibility by remember { mutableStateOf(false) }

    var email by remember {
        mutableStateOf(sharedPreferences.getString(PREF_EMAIL, "") ?: "")
    }
    var password by remember {
        mutableStateOf(sharedPreferences.getString(PREF_PASSWORD, "") ?: "")
    }
    var rememberMe by remember {
        mutableStateOf(sharedPreferences.getBoolean(PREF_REMEMBER_ME, false))
    }

    fun updateRememberMeState(value: Boolean) {
        rememberMe = value
        sharedPreferences.edit().putBoolean(PREF_REMEMBER_ME, value).apply()
    }

    var isLoading by remember { mutableStateOf(false) }

    // Enable the button only when all fields are filled
    var isButtonEnabled by remember {
        mutableStateOf(false)
    }

    // Validation checks for email and password fields
    // Enable the button only when all fields are filled
    if (email.isNotEmpty() && password.isNotEmpty()) {
        isButtonEnabled = true
    } else {
        isButtonEnabled = false
    }

    val emailError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }

    fun validateFields(): Boolean {
        var isValid = true

        // Email validation
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()
//            emailError.value = "Invalid email address"
            isValid = false
        } else {
            emailError.value = null
        }

        // Password validation
        if (password.isEmpty()) {
            Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
//            passwordError.value = "Password cannot be empty"
            isValid = false
        } else {
            passwordError.value = null
        }

        return isValid
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
            modifier = Modifier
                .padding(10.dp)
                .size(100.dp),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = "Welcome to Discover",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

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
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    val eyeIcon: ImageVector =
                        if (passwordVisibility) ImageVector.vectorResource(id = R.drawable.ic_eye_off)
                        else ImageVector.vectorResource(id = R.drawable.ic_eye)

                    Icon(
                        imageVector = eyeIcon,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { updateRememberMeState(it) },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp)
                )
                Text("Remember me")
            }
            Text(
                text = "Forget password",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate("ForgetPassword")
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (validateFields()) {
                    isLoading = true
                    val auth = Firebase.auth
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        isLoading = false
                        if (it.isSuccessful) {
                            if (rememberMe) {
                                sharedPreferences.edit {
                                    putString(PREF_EMAIL, email)
                                    putString(PREF_PASSWORD, password)
                                }
                            } else {
                                sharedPreferences.edit {
                                    remove(PREF_EMAIL)
                                    remove(PREF_PASSWORD)
                                }
                            }
                            navController.navigate("Home")
                        }
                    }.addOnFailureListener {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            },
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
                Text("Login", color = Color.White, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Don't have an account? ")
            Text(
                text = "Sign up",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate("Signup")
                }
            )
        }
    }
}