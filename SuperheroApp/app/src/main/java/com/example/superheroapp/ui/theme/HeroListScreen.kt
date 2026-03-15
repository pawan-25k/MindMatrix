package com.example.superheroapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.superheroapp.data.HeroesRepository


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroListScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Superheroes",
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(HeroesRepository.heroes) { hero ->
                HeroCard(hero = hero)
            }
            // Bottom spacing
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}