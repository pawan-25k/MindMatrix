import kotlin.math.ceil


fun main() {
    println("=== Tip Calculator (Auto-Report) ===")

    
    val billAmount = 1250.50
    val tipPercent = 15.0
    val roundUp = true 

    val tip = calculateTip(billAmount, tipPercent, roundUp)

    println("Bill Amount: ${formatCurrency(billAmount)}")
    println("Tip Percent: $tipPercent%")
    println("Tip Amount:  ${formatCurrency(tip)}")
    println("Total Bill:  ${formatCurrency(billAmount + tip)}")
    println("------------------------------------")
}

fun calculateTip(billAmount: Double, tipPercent: Double, roundUp: Boolean): Double {
    var tip = billAmount * tipPercent / 100
    if (roundUp) {
        tip = ceil(tip) 
    }
    return tip
}

fun formatCurrency(amount: Double): String {
  
    return "Rs. %.2f".format(amount)
}