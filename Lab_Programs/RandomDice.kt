import kotlin.random.Random

fun main() {
    println("=== Dice Roller Program ===")

    repeat(5) {
        println("\nRolling dice...")
        rollDice()
    }
}

fun rollDice() {
    val dice1 = generateDiceValue()
    val dice2 = generateDiceValue()

    printDiceResult(dice1, dice2)
    checkForDouble(dice1, dice2)
}

fun generateDiceValue(): Int {
    return Random.nextInt(1, 7)
}

fun printDiceResult(d1: Int, d2: Int) {
    println("Dice 1: $d1")
    println("Dice 2: $d2")
    println("Total : ${d1 + d2}")
}

fun checkForDouble(d1: Int, d2: Int) {
    if (d1 == d2) {
        println("You rolled a double! Great luck!")
    }
}