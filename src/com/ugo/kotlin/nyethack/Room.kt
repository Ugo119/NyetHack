package com.ugo.kotlin.nyethack

open class Room(val name: String){
    protected open val dangerLevel = 5
    var monster: Monster? = Goblin()
    fun description() = "Room: $name\n" +
            "Danger level: $dangerLevel\n" +
            "Creature: ${monster?.description ?: "none."}"
    open fun load() = "Nothing much to see here..."
}

open class TownSquare: Room("Town Square") {
    private var bellSound = "Gwong"
    final override fun load() = "The villagers rally and cheer as you enter!\n${ringBell()}"
    override val dangerLevel = super.dangerLevel - 3

    private fun ringBell() = "The bell tower announces your arrival. $bellSound"
}