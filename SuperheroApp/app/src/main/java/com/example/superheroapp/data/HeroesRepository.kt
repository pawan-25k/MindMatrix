package com.example.superheroapp.data
import com.example.superheroapp.R
import com.example.superherocatalog.model.Superhero

object HeroesRepository {
    val heroes = listOf(
        Superhero("Solaris", "Photo-kinetic Blasts", "Protecting the Earth's ozone layer from cosmic threats.", R.drawable.hero1),
        Superhero("Terra", "Geokinesis", "Restoring land affected by desertification and natural disasters.", R.drawable.hero2),
        Superhero("Nebula", "Star-stuff Manipulation", "Patrolling the outer rim to guide lost space travelers.", R.drawable.hero3),
        Superhero("Aegis", "Kinetic Absorption", "Acting as a shield for civilians during urban emergencies.", R.drawable.hero4),
        Superhero("Glint", "Luminescence", "Providing light and hope in the world's darkest regions.", R.drawable.hero5),
        Superhero("Deep Current", "Hydrokinesis", "Safeguarding the deep-sea ecosystems from illegal dredging.", R.drawable.hero6)
    )
}