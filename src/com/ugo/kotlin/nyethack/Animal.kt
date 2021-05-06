package com.ugo.kotlin.nyethack

class Animal(var breed: String, var name: String = "lalong", var age: Int) {
    constructor(breed: String): this(breed,
            age = 10)
    fun behaviourOne() {
        println("$name is $age years old and is a $breed")
    }
}