package com.estivensh4.maasapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.estivensh4.maasapp.domain.model.Screen
import com.estivensh4.maasapp.presentation.screens.LoginScreen
import com.estivensh4.maasapp.presentation.ui.theme.MaasAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaasAppTheme {
                val navController = rememberNavController()
                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LOGIN.name
                    ) {
                        composable(Screen.LOGIN.name) {
                            LoginScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaasAppTheme {
        Greeting("Android")
    }
}