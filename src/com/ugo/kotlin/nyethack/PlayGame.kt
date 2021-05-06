package com.ugo.kotlin.nyethack

import java.lang.Exception
import java.lang.IllegalStateException

object Game{
    private val player = Player("Madrigal")
    private var currentRoom: Room = TownSquare()

    private var worldMap = listOf(
            listOf(currentRoom, Room("Tavern"), Room("Back Room")),
            listOf(Room("Long Corridor"), Room("Generic Room"))
    )

    private fun move(directionInput: String) =
            try {
                val direction = Direction.valueOf(directionInput.toUpperCase())
                val newPosition = direction.updateCoordinate(player.currentPosition)
                if (!newPosition.isInBounds) {
                    throw IllegalStateException("$direction is out of bounds.")
                }
                val newRoom = worldMap[newPosition.y][newPosition.x]
                player.currentPosition = newPosition
                currentRoom = newRoom
                "Ok, you move $direction to the ${newRoom.name}. \n${newRoom.load()}"
            } catch(e: Exception) {
                "Invalid direction: $directionInput"
            }

    init {
        println("Welcome, adventurer.")
        player.castFireBall()
    }

    fun play() {
        while(true) {
            println(currentRoom.description())
            println(currentRoom.load())

            printPlayerStatus(player)

            print("> Enter your command: ")
            //println("Last command: ${readLine()}")
            println(GameInput(readLine()).processCommand())
        }
    }
    private fun printPlayerStatus(player: Player) {
        println("(Aura: ${player.getAuraColor()})" +
                "(Blessed: ${if (player.isBlessed)"Yes" else "No"})")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private class GameInput(arg: String?){
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1, { "" })

        fun processCommand() = when(command.toLowerCase()) {
            "move" -> move(argument)
            else -> commandNotFound()
        }

        private fun commandNotFound() = "I'm not quite sure what you are trying to do!"
    }

}