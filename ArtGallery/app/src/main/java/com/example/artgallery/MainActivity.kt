package com.example.artgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtGalleryApp()
        }
    }
}
data class Artwork(
    val imageRes: Int,
    val title: String,
    val artist: String
)
@Composable
fun ArtGalleryApp() {

    val artworks = listOf(
        Artwork(R.drawable.art1, "Mona Lisa", "Leonardo da Vinci"),
        Artwork(R.drawable.art2, "Starry Night", "Vincent van Gogh"),
        Artwork(R.drawable.art3, "Gear 2nd", "Echiro Oda")
    )

    var currentIndex by remember { mutableStateOf(0) }

    val currentArtwork = artworks[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(currentArtwork.imageRes),
                contentDescription = currentArtwork.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = currentArtwork.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = currentArtwork.artist,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(onClick = {
                currentIndex =
                    if (currentIndex - 1 < 0)
                        artworks.size - 1
                    else
                        currentIndex - 1
            }) {
                Text("Previous")
            }

            Button(onClick = {
                currentIndex =
                    (currentIndex + 1) % artworks.size
            }) {
                Text("Next")
            }
        }
    }
}