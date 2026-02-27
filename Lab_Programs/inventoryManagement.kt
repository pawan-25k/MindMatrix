// Generic data class for inventory items
data class Item<T>(
    val id: T,
    val name: String,
    val category: String,
    val price: Double,
    val stock: Int
)

class Inventory<T>(private val items: List<Item<T>>) {

    fun showAllItems() {
        println("\n=== All Inventory Items ===")
        for (item in items) {
            println("${item.name} | Category: ${item.category} | Price: ${item.price} | Stock: ${item.stock}")
        }
    }

    fun filterByStock(minStock: Int) {
        println("\n=== Items With Stock >= $minStock ===")
        for (item in items) {
            if (item.stock >= minStock) {
                println("${item.name} | Stock: ${item.stock}")
            }
        }
    }

    fun filterByCategory(category: String) {
        println("\n=== Items in Category: $category ===")
        for (item in items) {
            if (item.category.equals(category, ignoreCase = true)) {
                println("${item.name} | Price: ${item.price} | Stock: ${item.stock}")
            }
        }
    }

    fun computeCategoryTotals() {
        println("\n=== Category Totals ===")

        val categoryMap = HashMap<String, Double>()

        for (item in items) {
            val totalValue = item.price * item.stock

            if (categoryMap.containsKey(item.category)) {
                categoryMap[item.category] =
                    categoryMap[item.category]!! + totalValue
            } else {
                categoryMap[item.category] = totalValue
            }
        }

        for (entry in categoryMap.entries) {
            println("${entry.key} -> Total Value: ${"%.2f".format(entry.value)}")
        }
    }
}

fun main() {

    val inventory = Inventory(
        listOf(
            Item(1, "Laptop", "Electronics", 50000.0, 5),
            Item(2, "Phone", "Electronics", 20000.0, 10),
            Item(3, "Chair", "Furniture", 1500.0, 20),
            Item(4, "Table", "Furniture", 7000.0, 8),
            Item(5, "Notebook", "Stationery", 50.0, 100)
        )
    )

    inventory.showAllItems()
    inventory.filterByStock(10)
    inventory.filterByCategory("Electronics")
    inventory.computeCategoryTotals()
}