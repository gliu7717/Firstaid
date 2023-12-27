package com.derek.firstaidcardgame.ui.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.derek.firstaidcardgame.R
import com.derek.firstaidcardgame.model.Card

class FirstaidCardGameVM : ViewModel() {
    val cardListLD = MutableLiveData<List<Card>>()
    val winDialog = MutableLiveData<Boolean>()
    val loseDialog = MutableLiveData<Boolean>()
    val playerScore = MutableLiveData<Int>()
    var score = 0
    val remainingLives = MutableLiveData<Int>()
    var lives = 6
    private var cardList = mutableListOf<Card>()
    private val flippedCards = mutableListOf<Card>()
    private var canClick : Boolean = true

    private val cardImageResourceIds = arrayOf(
        R.drawable.cherries_no_bg,
        R.drawable.clementine_no_bg,
        R.drawable.melon_no_bg,
        R.drawable.pear_no_bg,
        R.drawable.pineapple_no_bg,
        R.drawable.banana_no_bg,
        R.drawable.watermelon_no_bg,
        R.drawable.tomato_no_bg)

    fun initializeCardList() {
        val allIndices = mutableListOf<Int>()

        for (i in cardImageResourceIds.indices) {
            allIndices.add(i)
            allIndices.add(i)
        }

        allIndices.shuffle()
        allIndices.shuffle()

        for (i in 0 until allIndices.size) {
            val randomIndex = allIndices[i]
            val imageResourceId = cardImageResourceIds[randomIndex]
            cardList.add(Card(i,imageResourceId, false, "Test"))
        }
        cardListLD.postValue(cardList)
    }

    private fun updateCard(updatedCard: Card) {
        val index = cardList.indexOfFirst { it.id == updatedCard.id }

        if (index != -1) {
            cardList[index] = updatedCard
            cardListLD.postValue(cardList)
        }
    }

    fun flipCard(card: Card) {
        if ((!card.isRevealed) && (canClick)){
            card.isRevealed = true
            flippedCards.add(card)
            updateCard(card)
        }
        if (flippedCards.size == 2) {
            canClick = false
            val card1 = flippedCards[0]
            val card2 = flippedCards[1]
            if (!areCardsMatching(card1, card2)) {
                wrongGuess(card1,card2)
            }
            else{
                rightGuess()
            }
            flippedCards.clear()
            if (checkIfFinish()){
                winDialog.postValue(true)
            }
        }

    }

    private fun areCardsMatching(card1: Card, card2: Card): Boolean {
        return card1.imageContent == card2.imageContent
    }

    private fun wrongGuess(card1: Card, card2: Card){
        score -= 25
        playerScore.postValue(score)
        lives -= 1
        remainingLives.postValue(lives)
        Handler(Looper.getMainLooper()).postDelayed({
            card1.isRevealed = false
            card2.isRevealed = false
            updateCard(card1)
            updateCard(card2)
            canClick=true
        }, 800)
        if (lives == 0){
            loseDialog.postValue(true)
        }
    }

    private fun rightGuess() {
        score+=100
        playerScore.postValue(score)
        canClick=true
    }

    private fun checkIfFinish() : Boolean = cardList.all { it.isRevealed }

    fun restartGame() {
        cardList.clear()
        initializeCardList()
        score = 0
        lives = 6
    }

}