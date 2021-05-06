package com.ugo.kotlin.nyethack

import java.io.File
import kotlin.math.roundToInt

const val TAVERN_NAME = "Taernyl's Folly"
var playerGold = 10
var playerSilver = 10
var pintQuantity = 40
val patronList = mutableListOf("Eli", "Mordoc", "Sophie", "Gredor")
val lastNames = listOf("IronFoot", "Fernsworth", "Baggins")
val uniquePatronNames = mutableSetOf<String>()
val menuList = File("data/tavern-menu-data.txt")
        .readText()
        .split("\n")

fun main(args: Array<String>) {

    placeOrder("shandy, Dragon's Breath, 5.91", 12, "Madrigal")
//    com.ugo.kotlin.nyethack.placeOrder("elixir, Shirley's Temple, 4.12")
    println(patronList)
    patronList.forEach { patron ->
        println("Good evening, $patron")
        placeOrder("shandy, Dragon's Breath, 5.91", 17, patron)
    }

    menuList.forEachIndexed { index, data ->
        println("${index + 1}: $data")
    }

    (0..9).forEach {
        val first = patronList.shuffled().first()
        val last = lastNames.shuffled().last()
        val name = "$first $last"
        uniquePatronNames += name
        println(name)
        println(uniquePatronNames)
    }

}

fun performPurchase(price: Double, pintsBought: Int){
    displayBalance()
    val totalPurse = playerGold + (playerSilver /100.0)
    println("Total purse: $totalPurse")
    println("Purchasing item for $price")

    val remainingBalance = totalPurse - price
    println("Remaining balance: ${"%.2f".format(remainingBalance)}")

    val remainingGold = remainingBalance.toInt()
    val remainingSilver = (remainingBalance % 1 * 100).roundToInt()
    playerGold = remainingGold
    playerSilver = remainingSilver

    pintQuantity -= pintsBought

    displayBalance()
}

private fun displayBalance() {
    println("com.ugo.kotlin.nyethack.Player's purse balance: Gold: $playerGold, Silver: $playerSilver, Pints: $pintQuantity")
}

private fun placeOrder(menuData: String, pintOrdered: Int, patronName: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("$patronName speaks with $tavernMaster about their order")
    val (type, name, price) = menuData.split(',')
    val message = "$patronName buys a $name ($type) for $price"
    println(message)

    println("$patronName ordered $pintOrdered pints of $name.")

    performPurchase(price.toDouble(), pintOrdered)
//    val phrase = "Ah, delicious $name!"
//    println("Madrigal exclaims: ${com.ugo.kotlin.nyethack.toDragonSpeak(phrase)}")
    val phrase = if (name == "Dragon's Breath") {
        "$patronName exclaims: ${toDragonSpeak("Ah, delicious $name!")}"
    } else {
        "$patronName says: Thanks for the $name"
    }

    println(phrase)
}

private fun toDragonSpeak(phrase: String) =
        phrase.replace(Regex("[aeiou]")) {
            when(it.value) {
                "a" -> "4"
                "e" -> "3"
                "i" -> "1"
                "o" -> "0"
                "u" -> "|_|"
                else -> it.value
            }
        }