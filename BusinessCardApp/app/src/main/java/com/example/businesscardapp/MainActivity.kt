package com.example.businesscardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscardapp.ui.theme.BusinessCardAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(color = 0xFF5219B4)
                ){
                    BusinessCard()

                }
            }
        }
    }
}

@Composable
fun BusinessCard(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all=24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(height = 40.dp))

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(size = 120.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(height = 16.dp))

        Text(
            text = "Pawan",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Android Developer",
            fontSize = 18.sp,
            color = Color(color = 0xFFC4B539),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(height = 48.dp))
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.wrapContentWidth()
        ) {
            ContactRow(icon = Icons.Default.Phone, text = "+91 1234567890")
            ContactRow(icon = Icons.Default.Email, text = "pawan@gmail.com")
            ContactRow(icon = Icons.Default.Place, text = "Bengaluru,India")
        }
    }
}
@Composable
fun ContactRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment= Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ){
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(size= 24.dp),
            tint = Color(color= 0xFF006D3B)
        )
        Spacer(modifier = Modifier.width(width= 16.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Black
        )
    }

}