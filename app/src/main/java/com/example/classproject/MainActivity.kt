
package com.example.classproject

import android.content.Context
import android.content.Intent
import androidx.compose.ui.res.painterResource  // for painterResource
import androidx.compose.ui.platform.LocalContext  // for LocalContext
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.classproject.ui.theme.ClassProjectTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.classproject.R.string.default_web_client_id
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth  // FirebaseAuth instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Configure Google Sign-In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        auth = FirebaseAuth.getInstance()

        // Initialize the ActivityResultLauncher
        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
            }
        }

        // Set the UI content
        setContent {
            ClassProjectTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (auth.currentUser != null) "welcome_screen" else "login_screen",  // Start at welcome if already logged in
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login_screen") {
                            LoginScreen(navController, googleSignInClient)
                        }
                        composable("register_screen") {
                            RegisterScreen(navController)
                        }
                        composable("welcome_screen") {
                            WelcomeScreen(navController)
                        }
                    }
                }
            }
        }
    }

    fun launchGoogleSignIn(signInIntent: Intent) {
        googleSignInLauncher.launch(signInIntent)  // Launch the intent for Google Sign-In
    }

    // Firebase authentication with the Google Sign-In token
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                val intent = Intent(this, MainActivity2::class.java).apply {}
                if (task.isSuccessful) {
                    // Navigate to welcome screen after login
                    Toast.makeText(this, "Sign-In Successful", Toast.LENGTH_SHORT).show()
                    startActivity(intent)


                    // Handle navigation in Composable by calling the NavController inside the Composable
                } else {
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }
}


@Composable
fun LoginScreen(
    navController: NavController,
    googleSignInClient: GoogleSignInClient,  // Pass googleSignInClient as a parameter
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Retrieve the context and SharedPreferences
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.ic_visibility)
                else
                    painterResource(id = R.drawable.ic_visibility_off)

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = image, contentDescription = null)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Retrieve stored credentials from SharedPreferences
                val storedUsername = sharedPreferences.getString("username", "")
                val storedPassword = sharedPreferences.getString("password", "")

                // Check if entered credentials match the stored ones
                if (username == storedUsername && password == storedPassword) {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
//                    navController.navigate("welcome_screen")
                } else {
                    Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button for Google Sign-In
        Button(
            onClick = {
                val signInIntent = googleSignInClient.signInIntent
                if (context is MainActivity) {
                    context.launchGoogleSignIn(signInIntent)
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = " Login with Google" )
        }
    }
}


@Composable
fun RegisterScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Save user data locally
                editor.putString("username", username)
                editor.putString("email", email)
                editor.apply()

                // Navigate to the welcome screen
                navController.navigate("welcome_screen")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Register")
        }
    }
}


@Composable
fun WelcomeScreen(navController: NavController) {
    val sharedPreferences = LocalContext.current.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username", "User")
    val editor = sharedPreferences.edit()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome, Adventurer", style = MaterialTheme.typography.headlineLarge)

        // Add Logout Button
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Clear stored user data
            editor.clear().apply()

            // Navigate back to the login screen
            navController.navigate("login_screen") {
                popUpTo("welcome_screen") { inclusive = true }
            }
        }) {
            Text(text = "Start")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ClassProjectTheme {
        LoginScreen(
            navController = rememberNavController(),  // Use a mock navController for preview
            modifier = Modifier,
            googleSignInClient = TODO()  // Replace with a mock or appropriate value for preview
        )
    }
}
