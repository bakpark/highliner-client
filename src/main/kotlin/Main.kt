package com.example;

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() {
    val clients = mutableListOf<HighlinerClient>()
    val clientSize = 100
    for (i in 1..clientSize) {
        val client = HighlinerClient(i+203, 5, 100)
        clients.add(client)
    }
    runBlocking {
        for (i in 0..<clientSize) {
            launch(Dispatchers.IO) { clients[i].start() }
        }
    }

}

