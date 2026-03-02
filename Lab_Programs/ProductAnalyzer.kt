data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val extra: String
)

class ProductAnalyzer(private val products: List<Product>) {

    fun applyDiscount(discountStrategy: (Double) -> Double): List<Product> {
        return products.map { product ->
            product.copy(price = discountStrategy(product.price))
        }
    }

    fun filterProducts(predicate: (Product) -> Boolean): List<Product> {
        return products.filter(predicate)
    }

    fun <R> transformProducts(transform: (Product) -> R): List<R> {
        return products.map(transform)
    }

    fun formatProducts(list: List<Product>): String {
        return list.joinToString("\n") { product ->
            "ID: ${product.id} | Name: ${product.name.padEnd(15)} | Category: ${product.category.padEnd(12)} | Price: Rs.${"%.2f".format(product.price)}"
        }
    }
}

fun main() {

    val productList = listOf(
        Product(1, "Laptop", "Electronics", 75000.0, "16GB RAM"),
        Product(2, "Phone", "Electronics", 30000.0, "128GB"),
        Product(3, "Shoes", "Fashion", 4000.0, "42"),
        Product(4, "Watch", "Accessories", 5000.0, "Analog"),
        Product(5, "Bag", "Fashion", 2500.0, "Leather")
    )

    val analyzer = ProductAnalyzer(productList)

    val discounted = analyzer.applyDiscount { price ->
        if (price > 10000) price * 0.9 else price * 0.95
    }

    val filtered = analyzer.filterProducts { product ->
        product.category == "Electronics" && product.price > 20000
    }

    val transformed = analyzer.transformProducts { product ->
        "${product.name} -> Rs.${"%.2f".format(product.price)}"
    }

    println("---- Discounted Products ----")
    println(analyzer.formatProducts(discounted))

    println("\n---- Filtered Products ----")
    println(analyzer.formatProducts(filtered))

    println("\n---- Transformed View ----")
    transformed.forEach { println(it) }
}