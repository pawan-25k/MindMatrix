abstract class Animal(
    val name: String,
    val age: Int
) {
    abstract fun makeSound()

    open fun eat() {
        println("$name is eating.")
    }

    fun info() {
        println("Name: $name | Age: $age")
    }
}

interface WaterSprayable {
    fun sprayWater()
}

interface Mimicable {
    fun mimic()
}

class Lion(name: String, age: Int) : Animal(name, age) {
    override fun makeSound() {
        println("$name roars loudly!")
    }
}

class Elephant(name: String, age: Int) : Animal(name, age), WaterSprayable {
    override fun makeSound() {
        println("$name trumpets!")
    }

    override fun sprayWater() {
        println("$name sprays water with its trunk!")
    }
}

class Parrot(name: String, age: Int) : Animal(name, age), Mimicable {
    override fun makeSound() {
        println("$name chirps!")
    }

    override fun mimic() {
        println("$name mimics human words!")
    }
}

fun main() {

    val zoo: List<Animal> = listOf(
        Lion("Simba", 5),
        Elephant("Dumbo", 10),
        Parrot("Polly", 2)
    )

    println("---- Zoo Animal Tracker ----")

    for (animal in zoo) {
        animal.info()
        animal.makeSound()
        animal.eat()

        if (animal is WaterSprayable) {
            animal.sprayWater()
        }

        if (animal is Mimicable) {
            animal.mimic()
        }

        println()
    }
}