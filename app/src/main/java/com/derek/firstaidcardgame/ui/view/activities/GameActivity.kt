package com.derek.firstaidcardgame.ui.view.activities

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.derek.firstaidcardgame.R
import com.derek.firstaidcardgame.model.Card
import com.derek.firstaidcardgame.databinding.ActivityGameBinding
import com.derek.firstaidcardgame.ui.view.CardsAdapter
import com.derek.firstaidcardgame.ui.viewmodel.FirstaidCardGameVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    //private val viewModel : CardGameVM by viewModels()
    private lateinit var  viewModel : FirstaidCardGameVM
    private lateinit var heartsList: List<ImageView>

    companion object{
        fun getScreenWidth() : Int {
            return Resources.getSystem().displayMetrics.widthPixels
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FirstaidCardGameVM::class.java)

        viewModel.initializeCardList()

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        heartsList = listOf(binding.firstLife,binding.secondLife,binding.thirdLife,binding.fourthLife,binding.fifthLive,binding.sixthLive)

        initRecyclerView()

        viewModel.remainingLives.observe(this){
            heartsList[it].visibility = View.INVISIBLE
        }

        viewModel.playerScore.observe(this){
            binding.tvScore.text = it.toString()
        }

        viewModel.winDialog.observe(this){
            winAlertDialog()
        }

        viewModel.loseDialog.observe(this){
            loseAlertDialog()
        }
    }

    private fun initRecyclerView() {
        binding.rvCardTable.layoutManager = GridLayoutManager(this, 4,RecyclerView.HORIZONTAL,false)
        viewModel.cardListLD.observe(this){
            binding.rvCardTable.adapter = CardsAdapter(it){ card -> onItemSelected(card)}
        }
    }

    private fun onItemSelected(card: Card) {
        viewModel.flipCard(card)
    }

    private fun winAlertDialog() {
        val winDialog = AlertDialog.Builder(this)

        winDialog.setTitle(R.string.win_dialog_title)
        winDialog.setMessage("You have completed this level with a total score of ${viewModel.score} points.")

        winDialog.setPositiveButton("FINISH") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            finish()
            resetLives()
        }

        winDialog.setNegativeButton("RESTART") {dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            viewModel.restartGame()
            resetLives()
        }

        winDialog.create().show()

    }

    private fun loseAlertDialog() {
        val loseDialog = AlertDialog.Builder(this)

        loseDialog.setTitle(R.string.lose_dialog_title)
        loseDialog.setMessage(R.string.lose_dialog_message)

        loseDialog.setPositiveButton("TRY AGAIN") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            viewModel.restartGame()
            resetLives()
        }

        loseDialog.setNegativeButton("GO BACK TO MENU") {dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            finish()
            resetLives()
        }

        loseDialog.create().show()
    }

    private fun resetLives(){
        heartsList.forEach {
            it.visibility = View.VISIBLE
        }
    }

}