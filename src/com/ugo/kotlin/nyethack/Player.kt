package com.ugo.kotlin.nyethack

import java.io.File

class Player(_name: String,
             var healthPoints: Int = 100,
             val isBlessed: Boolean,
             private val isImmortal: Boolean) {

    var name = _name
        get() = "${field.capitalize()} of $homeTown"
        set(value) {
            field = value.trim()
        }

    val homeTown by lazy {selectHomeTown()}
    var currentPosition = Coordinate(0,0)

    private fun selectHomeTown() = File("data/towns.txt")
            .readText()
            .split("\n")
            .shuffled()
            .first()

    init {
        require(healthPoints > 0, {"Healthpoints must be greater than zero."})
        require(name.isNotBlank(), {"Player must have a name."})
    }

    constructor(name: String): this(name,
            isBlessed = true,
            isImmortal = false) {
        if (name.toLowerCase() == "kar") healthPoints = 40
    }

    constructor(name: String, healthPoints: Int): this(name,
            healthPoints,
            isBlessed = true,
            isImmortal = false)



    fun castFireBall(numFireBalls: Int = 2) =
            println("A glass of fireball springs into existence. (x$numFireBalls)")

    fun getAuraColor(): String {
        val auraIsVisible = isBlessed && healthPoints > 50 || isImmortal
        return if (auraIsVisible) "GREEN" else "NONE"
    }

    fun formatHealthStatus() =
            when (healthPoints) {
                100 -> "is in good health!"
                in 90..99 -> "has a few scratches."
                in 75..89 -> if (isBlessed) {
                    "has some minor wounds but it's healing."
                } else {
                    "has some minor wounds."
                }
                in 15..74 -> "looks pretty hurt."
                else -> "is in awful condition"
            }
}