package com.example.composition.presentation

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishBinding

class GameFinishFragment : Fragment() {

    private val args by navArgs<GameFinishFragmentArgs>()

    private var _binding: FragmentGameFinishBinding? = null
    private val binding: FragmentGameFinishBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishBinding == null")

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
            tvScoreAnswers.text = String.format(getString(R.string.score_answers), args.gameResult.countOfRightAnswers)
            tvRequiredAnswers.text = String.format(getString(R.string.required_score), args.gameResult.gameSettings.minCountOfRightAnswers)
            tvRequiredPercentage.text = String.format(getString(R.string.required_percentage), args.gameResult.gameSettings.minPercentOfRightAnswers)
            tvScorePercentage.text = String.format(getString(R.string.score_percentage), getPercentOfRightAnswers())


        }
    }

    private fun getPercentOfRightAnswers(): Int{
        with(args.gameResult){
            if (countOfQuestions == 0){
                return 0
            }else{
                return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
            }
        }
    }

    private fun setUpImage(): Int{
        return if(args.gameResult.winner){
            R.drawable.ic_smile
        }else{
            R.drawable.ic_sad
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame(){
        findNavController().popBackStack()
    }

}