package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentChooseLevelBinding
import com.example.composition.domain.entities.Level
import com.example.composition.presentation.GameFragment.Companion.LEVEL_PARAM

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLevelEasy.setOnClickListener { launchGameFragment(Level.EASY) }
        binding.buttonLevelNormal.setOnClickListener { launchGameFragment(Level.MEDIUM) }
        binding.buttonLevelHard.setOnClickListener { launchGameFragment(Level.HARD) }
        binding.buttonLevelTest.setOnClickListener { launchGameFragment(Level.TEST) }
    }

    private fun launchGameFragment(level: Level){
        val args = Bundle().apply {
            putParcelable(LEVEL_PARAM, level)
        }
        findNavController().navigate(R.id.action_chooseLevelFragment_to_gameFragment, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val FRAGMENT_NAME = "ChooseLevelFragment"

        fun newInstance(): ChooseLevelFragment{
            return ChooseLevelFragment()
        }
    }
}