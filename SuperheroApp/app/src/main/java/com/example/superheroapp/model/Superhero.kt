package com.example.superherocatalog.model

import androidx.annotation.DrawableRes

data class Superhero(
    val name: String,
    val power: String,
    val mission: String,
    @DrawableRes val imageRes: Int
)