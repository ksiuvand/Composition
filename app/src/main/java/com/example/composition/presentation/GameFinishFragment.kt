package com.example.composition.presentation

import android.app.FragmentManager
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishBinding
import com.example.composition.domain.entities.GameResult
import com.example.composition.domain.entities.Level
import com.example.composition.presentation.GameFragment.Companion.LEVEL_PARAM

class GameFinishFragment : Fragment() {
    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishBinding? = null
    private val binding: FragmentGameFinishBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener { retryGame() }
        with(binding){
            emojiResult.setImageResource(setUpImage())
            tvScoreAnswers.text = String.format(getString(R.string.score_answers), gameResult.countOfRightAnswers)
            tvRequiredAnswers.text = String.format(getString(R.string.required_score), gameResult.gameSettings.minCountOfRightAnswers)
            tvRequiredPercentage.text = String.format(getString(R.string.required_percentage), gameResult.gameSettings.minPercentOfRightAnswers)
            tvScorePercentage.text = String.format(getString(R.string.score_percentage), getPercentOfRightAnswers())


        }
    }

    private fun getPercentOfRightAnswers(): Int{
        with(gameResult){
            if (countOfQuestions == 0){
                return 0
            }else{
                return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
            }
        }
    }

    private fun setUpImage(): Int{
        return if(gameResult.winner){
            R.drawable.ic_smile
        }else{
            R.drawable.ic_sad
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArguments(){
        gameResult = when {
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU -> {
                arguments?.getParcelable<GameResult>(GAME_RESULT_PARAM, GameResult::class.java)
            }

            else -> {
                @Suppress("DEPRECATION")
                arguments?.getParcelable<GameResult>(GAME_RESULT_PARAM)
            }
        }!!
    }

    private fun retryGame(){
        findNavController().popBackStack()
    }

    companion object{
        const val GAME_RESULT_PARAM = "game_result"
        const val FRAGMENT_NAME = "GameFinishFragment"

        fun newInstance(gameResult: GameResult): GameFinishFragment{
            return GameFinishFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT_PARAM, gameResult)
                }
            }
        }
    }

}