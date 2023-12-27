package com.derek.firstaidcardgame.model

data class Card(
    val id : Int,
    var imageContent : Int,
    var isRevealed : Boolean,
    var revealedText: String
)
