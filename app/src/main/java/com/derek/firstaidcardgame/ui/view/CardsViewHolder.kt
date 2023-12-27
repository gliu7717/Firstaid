package com.derek.firstaidcardgame.ui.view

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.derek.firstaidcardgame.R
import com.derek.firstaidcardgame.model.Card
import com.derek.firstaidcardgame.databinding.CardsViewBinding
import com.derek.firstaidcardgame.ui.view.activities.GameActivity

class CardsViewHolder(view : View) : ViewHolder(view) {

    private val binding = CardsViewBinding.bind(view)

    private val cardWidth = GameActivity.getScreenWidth()

    private val params: ViewGroup.LayoutParams = binding.cvCardItem.layoutParams

    fun bind(card: Card, onClickListener : (Card) -> Unit){
        params.width = cardWidth/5
        binding.cvCardItem.layoutParams = params
        binding.ivCardImage.setImageResource(R.drawable.card_back)
        binding.tvCardText.visibility = TextView.INVISIBLE
        binding.cvCardItem.setOnClickListener {
            onClickListener(card)
        }
    }

    fun bindFlipped(card : Card){
        params.width = cardWidth/5
        binding.cvCardItem.layoutParams = params
        binding.ivCardImage.scaleType = ImageView.ScaleType.CENTER_INSIDE
        binding.tvCardText.visibility = TextView.VISIBLE
        binding.ivCardImage.setImageResource(card.imageContent)
    }

}