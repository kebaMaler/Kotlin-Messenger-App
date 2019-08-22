package sola.martin.kotlinmessenger.models

class ChatMessage(val id: String, val text: String, val fomId: String, val toId: String, val timestamp: Long ){
        constructor() : this("","","","", -1)
    }
