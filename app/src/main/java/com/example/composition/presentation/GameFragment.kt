package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entities.GameResult
import com.example.composition.domain.entities.Level

class GameFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[GameViewModel::class.java]
    }

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.startGame(level)
    }

    private fun observeViewModel(){
        viewModel.currentTime.observe(viewLifecycleOwner){
            binding.tvTimer.text = it
        }
        viewModel.question.observe(viewLifecycleOwner){
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            val tvOptions = mutableListOf<TextView>().apply {
                add(binding.tvOption1)
                add(binding.tvOption2)
                add(binding.tvOption3)
                add(binding.tvOption4)
                add(binding.tvOption5)
                add(binding.tvOption6)
            }
            for (i in 0..tvOptions.size-1){
                val tvOption = tvOptions[i]
                tvOption.text = it.options[i].toString()
                tvOption.setOnClickListener {
                    viewModel.chooseAnswer(tvOption.text.toString().toInt())
                }
            }
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.text = it
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner){
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.setTextColor(setColor(it))
        }
        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner){
            binding.progressBar.progressTintList = ColorStateList.valueOf(setColor(it))
        }
        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner){
            launchGameFinishedFragment(it)
        }
    }

    private fun setColor(isEnough: Boolean): Int{
        val colorResId = if (isEnough){
            android.R.color.holo_green_light
        }else{
            android.R.color.holo_red_light
        }
        val color = ContextCompat.getColor(requireContext(), colorResId)
        return color
    }

    private fun launchGameFinishedFragment(gameResult: GameResult){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishFragment.newInstance(gameResult))
            .addToBackStack(GameFinishFragment.FRAGMENT_NAME)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArguments(){
        level = when {
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU -> {
                arguments?.getParcelable<Level>(LEVEL_PARAM, Level::class.java)
            }
            else -> {
                @Suppress("DEPRECATION")
                arguments?.getParcelable<Level>(LEVEL_PARAM)
            }
        }!!
    }

    companion object{

        const val LEVEL_PARAM = "level"
        const val FRAGMENT_NAME = "GameFragment"

        fun newInstance(level: Level): GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LEVEL_PARAM, level)
                }
            }
        }
    }
}