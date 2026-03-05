data class Hero(
    val name: String,
    val power: String,
    val mission: String
)

fun displayHeader() {
    println("======================================")
    println("           SUPERHERO CATALOG          ")
    println("======================================")
    println()
}

fun displayHero(hero: Hero) {
    println("--------------------------------------")
    println("Hero Name : ${hero.name}")
    println("Power     : ${hero.power}")
    println("Mission   : ${hero.mission}")
    println("--------------------------------------")
    println()
}

fun displayCatalog(heroes: List<Hero>) {
    for (hero in heroes) {
        displayHero(hero)
    }
}

fun main() {

    val heroes = listOf(
        Hero("Captain Code", "Super Intelligence", "Protect the digital world from cyber threats"),
        Hero("Thunder Byte", "Electric Speed", "Defend the city from tech villains"),
        Hero("Shadow Debugger", "Error Detection", "Find and eliminate system bugs"),
        Hero("Data Guardian", "Data Shield", "Secure sensitive information worldwide")
    )

    displayHeader()
    displayCatalog(heroes)

    println("Total Heroes Registered: ${heroes.size}")
}