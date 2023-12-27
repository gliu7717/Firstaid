package com.derek.firstaidcardgame.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.derek.firstaidcardgame.R
import com.derek.firstaidcardgame.model.Card
import com.derek.firstaidcardgame.ui.view.CardsViewHolder

class CardsAdapter(private val cardList : List<Card>, private val onClickListener : (Card) -> Unit) : RecyclerView.Adapter<CardsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CardsViewHolder(layoutInflater.inflate(R.layout.cards_view,parent,false))
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val card = cardList[position]

        if (card.isRevealed){
            holder.bindFlipped(card)
        }else{
            holder.bind(card, onClickListener)
        }
    }

    override fun getItemCount(): Int = cardList.size
}