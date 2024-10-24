package com.example.classproject

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.classproject.ui.theme.ClassProjectTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClassProjectTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home_screen",  // Now start with the home screen
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home_screen") {
                            HomeScreen(navController = navController)
                        }
                        composable("profile_screen") {  // Example of another page
                            ProfileScreen(navController = navController)
                        }
                        composable("settings_screen") {  // Example of another page
                            SettingsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome, Ivan!", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Navigate to Profile Page
        Button(onClick = {
            navController.navigate("profile_screen")
        }) {
            Text(text = "Go to Profile")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigate to Settings Page
        Button(onClick = {
            navController.navigate("settings_screen")
        }) {
            Text(text = "Go to Settings")
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile Page", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "This is where your profile info will be displayed!", style = MaterialTheme.typography.bodyMedium

        )

        Button(onClick = {
            navController.navigate("home_screen")
        }) {
            Text(text = "Back to Home")
        }
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Settings Page", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "More settings forthcoming!", style = MaterialTheme.typography.bodyMedium)


                Button(onClick = {
            navController.navigate("home_screen")
        }) {
            Text(text = "Back to Home")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ClassProjectTheme {
        HomeScreen(
            navController = rememberNavController()  // Use a mock navController for preview
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ClassProjectTheme {
        ProfileScreen(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    ClassProjectTheme {
        SettingsScreen(
            navController = rememberNavController()
        )
    }
}
