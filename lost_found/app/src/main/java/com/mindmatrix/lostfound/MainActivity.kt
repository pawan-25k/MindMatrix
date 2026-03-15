package com.mindmatrix.lostfound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mindmatrix.lostfound.navigation.AppNavGraph
import com.mindmatrix.lostfound.ui.theme.LostFoundTheme

/**
 * MainActivity – the single activity that hosts the entire Compose UI.
 *
 * Using a single Activity with Compose Navigation is the recommended Modern
 * Android Development pattern. Adding new screens only requires:
 *   1. Adding a new [Screen] entry in Screen.kt.
 *   2. Adding a composable {} block in AppNavGraph.kt.
 *
 * No changes to this file are needed when adding new screens.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Edge-to-edge renders content behind system bars for a modern look
        enableEdgeToEdge()

        setContent {
            LostFoundTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // NavController is created here and passed to the nav graph.
                    // It lives as long as MainActivity's composition.
                    val navController = rememberNavController()
                    AppNavGraph(navController = navController)
                }
            }
        }
    }
}
